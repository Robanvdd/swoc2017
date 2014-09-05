#!/usr/bin/env python

import glob
import optparse
import os
import platform
import shutil
import stat
import subprocess
import sys
import zipfile

class RedirectStdStreams(object):
    def __init__(self, stdout=None, stderr=None):
        self._stdout = stdout or sys.stdout
        self._stderr = stderr or sys.stderr

    def __enter__(self):
        self.old_stdout, self.old_stderr = sys.stdout, sys.stderr
        self.old_stdout.flush(); self.old_stderr.flush()
        sys.stdout, sys.stderr = self._stdout, self._stderr

    def __exit__(self, exc_type, exc_value, traceback):
        self._stdout.flush(); self._stderr.flush()
        sys.stdout = self.old_stdout
        sys.stderr = self.old_stderr

class Language:
    JAVA = 1
    PYTHON = 2

codeSubdir = "code"
allowedLanguages = ['java', 'python']

def touch(path):
    with open(path, 'a'):
        os.utime(path, None)

def remove(path):
    try:
        os.remove(path)
    except OSError:
        pass

def unzip(path):
    # unzip a file
    zfile = zipfile.ZipFile(path)
    for name in zfile.namelist():
        (dirname, filename) = os.path.split(name)
        if filename == '':
            # directory
            if not os.path.exists(dirname):
                os.mkdir(dirname)
        else:
            # file
            fd = open(name, 'wb')
            fd.write(zfile.read(name))
            fd.close()
    zfile.close()

def chdir_to_bot(botPath):
    if not os.path.isdir(botPath):
        sys.stderr.write("Error: Bot has no directory: " + botPath)
        sys.exit(1)

    os.chdir(botPath)

def remove_old_bot():
    if os.path.isdir(codeSubdir):
        shutil.rmtree(codeSubdir)

def extract_bot():
    # Working from bot-id directory
    zipFiles = glob.glob("*.zip")
    if not zipFiles:
    	sys.stderr.write("Error: No zip file found")
    	sys.exit(1)

    zipFileName = zipFiles[0]
    if len(zipFiles) > 1:
        sys.stderr.write("Warning: More than 1 zip file found, using first: " + zipFileName)
    print("Using zip file " + zipFileName)

    zipFilePath = os.path.join(os.getcwd(), zipFileName)
    print zipFilePath
    os.mkdir(codeSubdir)
    os.chdir(codeSubdir)
    unzip(zipFilePath)
    print("Unzip succesfull")
    os.chdir("..")

def store_compile_output(txt):
    # Working from bot-id directory
    with open("compile_output.txt", "w") as f:
        f.write(txt)

def run_ant_clean_build():
    # Working from bot-id directory
    os.chdir(codeSubdir)
    cwd = os.getcwd()
    print("Compiling from working directory: " + cwd)
    p = subprocess.Popen(["ant", "-noinput", "clean-build"], cwd=cwd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    antStdout = ""
    while p.returncode == None:
        antStdout += str(p.communicate()[0])

    os.chdir("..")
    store_compile_output(antStdout)

    if p.returncode == 0:
        print("Compilation succesfull")
    else:
        sys.stderr.write("Error: Something went wrong during compiling. Check the create file for compile output.")
        sys.exit(1)

def create_ant_run_script():
    # Working from bot-id directory
    with open("run.sh", "w") as f:
        f.write("ant run")
    st = os.stat("run.sh")
    os.chmod("run.sh", st.st_mode | stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH)

def determine_language():
    language = None
    os.chdir(codeSubdir)

    # Test for java, which is true when there is a build.xml
    if os.path.isfile("build.xml"):
        language = Language.JAVA

    # Else, test for python, which is true when there is a single .py file in the root directory
    elif glob.glob("*.py"):
        language = Language.PYTHON

    os.chdir("..")
    return language

def main():
    p = optparse.OptionParser()
    p.add_option('--path', '-p')
    options, arguments = p.parse_args()

    if not options.path:
        p.error('Bot path not given (-p)')

    chdir_to_bot(options.path)

    # Assume failure
    touch(os.path.join(os.getcwd(), 'COMPILE_FAILED'))

    stdoutFilePath = os.path.join(os.getcwd(), 'compiler_stdout.txt');
    stderrFilePath = os.path.join(os.getcwd(), 'compiler_stderr.txt');
    print("stdout will redirect to " + stdoutFilePath);
    print("stderr will redirect to " + stderrFilePath);

    with open(stdoutFilePath, 'w') as stdoutFile:
        with open(stderrFilePath, 'w') as stderrFile:
            with RedirectStdStreams(stdout=stdoutFile,stderr=stderrFile):
                remove_old_bot()
                extract_bot()

                language = determine_language()

                if platform.system() is "Windows":
                    sys.stderr.write('Error: Compiling not supported on windows.')
                    sys.exit(1)

                if language == Language.JAVA:
                    print("Detected language is Java")
                    run_ant_clean_build()
                    create_ant_run_script()
                elif language == Language.PYTHON:
                    print("Detected language is Python")
                    create_pyton_run_script()
                else:
                    sys.stderr.write("Error: Code was not written in a valid language")
                    sys.exit(1)

    # If this is reached: success
    remove(os.path.join(os.getcwd(), 'COMPILE_FAILED'))
    touch(os.path.join(os.getcwd(), 'COMPILE_SUCCESS'))


if __name__ == '__main__':
    main()
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
    CSHARP = 2

codeSubdir = "code"
allowedLanguages = ['java', 'csharp']

def touch(path):
    with open(path, 'a'):
        os.utime(path, None)

def remove(path):
    try:
        os.remove(path)
    except OSError:
        pass

#
# Extraction methods
#

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

#
# Java methods
#

def get_first_jar_name():
    os.chdir(codeSubdir)
    jar = glob.glob("*.jar")[0]
    os.chdir("..")
    return jar

def create_jar_run_script(jarName):
    # Working from bot-id directory
    with open("run.sh", "w") as f:
        f.write("java -jar code/" + jarName)
    st = os.stat("run.sh")
    os.chmod("run.sh", st.st_mode | stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH)


#
# C-Sharp methods
#

def get_first_exe_name():
    os.chdir(codeSubdir)
    exe = glob.glob("*.exe")[0]
    os.chdir("..")
    return exe

def create_csharp_run_script(exeName):
    # Working from bot-id directory
    with open("run.sh", "w") as f:
        f.write("code/" + exeName)
    
    st = os.stat("run.sh")
    os.chmod("run.sh", st.st_mode | stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH)

    st = os.stat("code/" + exeName)
    os.chmod("code/" + exeName, st.st_mode | stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH)

#
# Language determination
#

def determine_language():
    language = None
    os.chdir(codeSubdir)

    # Test for Java, which is true when there is a single .jar file in the root directory
    if glob.glob("*.jar"):
        if len(glob.glob("*.jar")) > 1:
            sys.stderr.write("Error: Invalid java bot. The top level directory can only contain one jar file.")
            sys.exit(1)
        language = Language.JAVA

    # Else, test for csharp, which is true when there is a single .exe file in the root directory
    elif glob.glob("*.exe"):
        language = Language.CSHARP

    os.chdir("..")
    return language

#
# Main
#

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

                if language == Language.JAVA:
                    print("Detected language is Java")
                    jarName = get_first_jar_name()
                    create_jar_run_script(jarName)
                elif language == Language.CSHARP:
                    print("Detected language is C#")
                    exeName = get_first_exe_name()
                    create_csharp_run_script(exeName)
                else:
                    sys.stderr.write("Error: Code was not written in a valid language")
                    sys.exit(1)

    # If this is reached: success
    remove(os.path.join(os.getcwd(), 'COMPILE_FAILED'))
    touch(os.path.join(os.getcwd(), 'COMPILE_SUCCESS'))


if __name__ == '__main__':
    main()
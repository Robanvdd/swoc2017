#!/usr/bin/env python

import glob
import optparse
import os
import shutil
import subprocess
import sys
import zipfile

codeSubdir = ".\\code"
javacPath = "\"C:\\Program Files\\Java\jdk1.7.0_45\\bin\\javac\""

class Language:
    JAVA = 1
    PYTHON = 2

def chdir_to_bot(botId):
	if not os.path.isdir(botId):
		sys.stderr.write("Error: Bot has no directory")
		sys.exit(1)

	os.chdir(botId)

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
		sys.stderr.write("Warning: More than 1 zip file found." + zipFileName)
	print("Using zip file " + zipFileName)

	with zipfile.ZipFile(zipFileName, "r") as z:
		z.extractall(codeSubdir)

def store_compile_output(txt):
	# Working from bot-id directory
	with open("compile_output.txt", "w") as f:
		f.write(txt)

def run_ant_clean_build():
	# Working from bot-id directory
	os.chdir(codeSubdir)
	cwd = os.getcwd()
	print("Compiling from working directory: " + cwd)
	p = subprocess.Popen(["D:\\apache-ant-1.9.4\\bin\\ant.bat", "-noinput", "clean-build"], cwd=cwd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
	antStdout = ""
	while p.returncode == None:
		antStdout += str(p.communicate()[0])

	os.chdir("..")
	store_compile_output(antStdout)

	if p.returncode == 0:
		print("Compilation succesfull")
	else:
		print("Error: Something went wrong during compiling. Check the create file for compile output.")
		sys.exit(1)

def create_ant_run_script():
	# Working from bot-id directory
	with open("run.sh", "w") as f:
		f.write("ant run")

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
	p.add_option('--bot', '-b')
	options, arguments = p.parse_args()
	
	if not options.bot:
		p.error('Bot id not given (-b)')

	chdir_to_bot(options.bot)
	remove_old_bot()
	extract_bot()

	language = determine_language()
	if language == Language.JAVA:
		print("Code language is Java")
		run_ant_clean_build()
		create_ant_run_script()
	elif language == Language.PYTHON:
		print("Code language is Python")
		create_pyton_run_script()
	else:
		sys.stderr.write("Error: Code was not written in a valid language")
		sys.exit(1)

if __name__ == '__main__':
	main()
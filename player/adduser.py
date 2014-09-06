#!/usr/bin/env python

import optparse
import os
#import bcrypt
import subprocess

def main():
    p = optparse.OptionParser()
    p.add_option('--username', '-u')
    p.add_option('--password', '-p')
    options, arguments = p.parse_args()

    if not options.username:
        p.error('Username not given (-u)')
    if not options.password:
        p.error('Password not given (-p)')

    hashed = 'test' #bcrypt.hashpw(options.password, bcrypt.gensalt(10))

    addUserString = "db.users.insert({username: '" + options.username + "', email: '', password: '" + hashed + "', roles: ['user']})"

    p = subprocess.Popen(["mongo ", "--quiet", "--eval", '"' + addUserString + '"'])

if __name__ == '__main__':
    main()
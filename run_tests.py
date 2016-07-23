#!/usr/bin/env python

# Author: Momchil Peychev

import sys
from os import listdir, system
from os.path import isfile, join, splitext
from subprocess import call
from termcolor import colored

TEST_PATH = './tests'

def main():
  inputs = [f for f in listdir(TEST_PATH) if isfile(join(TEST_PATH, f)) and f.endswith('.in')]
  for source in inputs:
    sys.stdout.write('Running ' + join(TEST_PATH, source) + ' ') 
    input_file = join(TEST_PATH, source)
    output_file = join(TEST_PATH, splitext(join(source))[0] + '.sol')
    status = system('java -jar Villie.jar ' + input_file + ' > mo.out && diff -w ' + output_file + ' mo.out')
    if status == 0:
      print colored('OK', 'green')
    else:
      print colored('FAIL', 'red')
      break
    raw_input("Press Enter to continue...")
  system('rm mo.out')

if __name__ == '__main__':
  main()

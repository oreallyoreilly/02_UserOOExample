#!/bin/bash
#ref: https://stackoverflow.com/questions/59895/getting-the-source-directory-of-a-bash-script-from-within

thisDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# echo "Current Dir:"
# echo $thisDir

cd $thisDir
clear

#test -help option
#java -cp target/02_UserOOExample-v1.jar com.oreallyoreilly.UserOOExample.App -help

#test no database specified
#java -cp target/02_UserOOExample-v1.jar com.oreallyoreilly.UserOOExample.App

#test database specified but file does not exiist
#java -cp target/02_UserOOExample-v1.jar com.oreallyoreilly.UserOOExample.App -d oreallyoreilly.db

#development where the JAR is in the target folder and the database in the database folder
java -cp target/02_UserOOExample-v1.jar com.oreallyoreilly.UserOOExample.App -d /Volumes/DataHD/Dropbox/_DEV2017/JAVA-BASIC/02_UserOOExample/database/oreallyoreilly.db

#deployment where the JAR and database are in the same folder
#java -cp 02_UserOOExample-v1.jar com.oreallyoreilly.UserOOExample.App -d oreallyoreilly.db

echo "Press ENTER to close window ..."
read aKey


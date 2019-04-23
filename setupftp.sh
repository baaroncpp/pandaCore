#!/bin/sh

# Get docker image for FTP
exec docker pull stilliard/pure-ftpd:hardened

# Start the FTP server
exec docker run -e FTP_USER_NAME=panda -e FTP_USER_PASS=123456 -e FTP_USER_HOME=/home/bob --name ftp_server stilliard/pure-ftpd:hardened


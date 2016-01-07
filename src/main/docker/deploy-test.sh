#/bin/bash
docker pull cyranob/satellit
docker stop poc_test
docker rm poc_test
CID=`docker run -d -p 8080:8080 -P --name poc_test cyranob/satellit`
echo $CID

fifo=/tmp/tmpfifo.$$
mkfifo "${fifo}" || exit 1
#tail -f logfile.log >${fifo} &
sudo tail -f /var/lib/docker/containers/$CID/$CID-json.log >${fifo} &
tailpid=$! # optional
grep -m 1 "Tomcat started" "${fifo}"
sudo kill "${tailpid}" # optional
rm "${fifo}"
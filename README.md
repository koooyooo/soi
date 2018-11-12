# SOI

## Post Access
```bash
# Httpie
$ http -j "localhost:9000/sois" Hello=World

# Curl
$ curl -X POST -H "Content-Type: application/json" -d '{"Hello":"World"}' localhost:9000/sois
$ curl -X POST -H "Content-Type: application/json" -d '{"Hello":"World"}' localhost:9000/sois -s -o /dev/null -w  "%{time_starttransfer}\n"
```
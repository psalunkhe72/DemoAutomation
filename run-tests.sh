DOCKER COMMANDS TO USE
# -----------------------------
# STEP 1: Check existing containers and networks
# -----------------------------
docker ps -a
docker network ls

# -----------------------------
# STEP 2: Stop any running Selenium Grid containers
# -----------------------------
docker stop selenium-hub
docker stop chrome-node
docker stop firefox-node

# -----------------------------
# STEP 3: Remove Selenium Grid containers
# -----------------------------
docker rm selenium-hub
docker rm chrome-node
docker rm firefox-node

# -----------------------------
# STEP 4: Remove Selenium Grid network (if exists)
# -----------------------------
docker network rm demoautomation_default

#------------------------------
#Add new network

# Create a new network
docker network create demoautomation_default

# -----------------------------
# STEP 5: Start Selenium Hub manually
# -----------------------------
docker run -d --name selenium-hub -p 4444:4444 selenium/hub:4.35.0

# -----------------------------
# STEP 6: Start Chrome and Firefox nodes manually
# -----------------------------
docker run -d --name chrome-node --link selenium-hub:hub selenium/node-chrome:4.35.0
docker run -d --name firefox-node --link selenium-hub:hub selenium/node-firefox:4.35.0

# -----------------------------
# STEP 7: Verify the Hub and Nodes
# -----------------------------
docker ps
curl http://localhost:4444/status

# -----------------------------
# STEP 8: Run your TestNG tests on the Grid
# -----------------------------
mvn clean test -Denv=grid -Dsurefire.suiteXmlFiles=testng.xml

# -----------------------------
# STEP 9: Stop the Grid after testing
# -----------------------------
docker stop selenium-hub chrome-node firefox-node
docker rm selenium-hub chrome-node firefox-node
docker network rm demoautomation_default

#!/bin/bash

# build both library and example
echo -e "\nStarting maven build"
mvn clean install

# generate javadoc jar for library
echo -e "\nGenerating JavaDoc for library"
cd library
mvn javadoc:jar

echo -e "\nCopying non-provided dependencies for examples"
cd ../example
mvn dependency:copy-dependencies  -DexcludeScope=provided
cd ../example_2
mvn dependency:copy-dependencies  -DexcludeScope=provided

# copy things over to citygrid-example-eclipse-project and citygrid-example-eclipse-project-2
echo -e "\nCopying over Android Eclipse project template"
cd ..
cp -r example-eclipse citygrid-example-eclipse-project
cp -r example-eclipse citygrid-example-eclipse-project-2

echo -e "\nCopying over source code"
rsync -vr --exclude=".settings/" --exclude="pom.xml" --exclude="gen/" --exclude="target/" --exclude=".classpath" --exclude=".project" --exclude="keystore/" --exclude="*.iml" --exclude="default.properties" example/ citygrid-example-eclipse-project/
rsync -vr --exclude=".settings/" --exclude="pom.xml" --exclude="gen/" --exclude="target/" --exclude=".classpath" --exclude=".project" --exclude="keystore/" --exclude="*.iml" --exclude="default.properties" example_2/ citygrid-example-eclipse-project-2/

echo -e "\nCopying over dependencies"
rsync -v example/target/dependency/*.jar citygrid-example-eclipse-project/libs/
rsync -v example_2/target/dependency/*.jar citygrid-example-eclipse-project-2/libs/

# zip everything up and drop at project level
echo -e "\nZip everything up"
zip -r citygrid-example-eclipse-project.zip citygrid-example-eclipse-project/ citygrid-example-eclipse-project-2/
zip -j citygrid-all.zip library/target/citygrid-*.jar example/target/citygrid-example-*-aligned.apk citygrid-example-maven-project.zip citygrid-example-eclipse-project.zip

cd example
mvn clean
cd ..
cd example_2
mvn clean
cd ..
zip -r citygrid-example-maven-project.zip example/ example_2/
zip -j citygrid-all.zip citygrid-example-maven-project.zip

# cleaning up
echo -e "\nCleaning up"
rm -rf citygrid-example-eclipse-project
rm -rf citygrid-example-eclipse-project-2
rm -rf citygrid-example-eclipse-project.zip
rm -rf citygrid-example-maven-project.zip

echo -e "\nDONE"

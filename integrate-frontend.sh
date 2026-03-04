#!/bin/bash
echo "🚀 Building Angular frontend..."
cd frontend-angular
npm install
ng build --prod
cd ..

echo "📦 Copying frontend to Spring Boot static..."
rm -rf src/main/resources/static/*
cp -r frontend-angular/dist/*/* src/main/resources/static/ 2>/dev/null || cp -r frontend-angular/dist/* src/main/resources/static/

echo "🔨 Building Spring Boot backend..."
mvn clean package -DskipTests

echo "🔄 Restarting service..."
sudo systemctl restart back

echo "✅ Done! Application disponible sur http://localhost:8080"

#!/bin/bash

# Script para instalar plugins necesarios en Jenkins
echo "Installing Jenkins plugins..."

# Lista de plugins necesarios
plugins=(
    "docker-workflow"
    "kubernetes"
    "kubernetes-cli"
    "kubernetes-client-api"
    "pipeline-stage-view"
    "workflow-aggregator"
    "git"
    "maven-plugin"
    "htmlpublisher"
    "junit"
    "email-ext"
    "build-timeout"
    "credentials-binding"
    "timestamper"
    "ws-cleanup"
    "ant"
    "gradle"
    "pipeline-github-lib"
    "pipeline-stage-view"
    "pipeline-graph-analysis"
    "pipeline-input-step"
    "pipeline-milestone-step"
    "pipeline-model-definition"
    "pipeline-rest-api"
    "pipeline-stage-tags-metadata"
    "pipeline-utility-steps"
    "ssh-slaves"
    "matrix-auth"
    "pam-auth"
    "ldap"
    "email-ext"
    "mailer"
    "slack"
    "discord-notifier"
    "telegram-notifications"
    "matrix-project"
    "resource-disposer"
    "ssh-credentials"
    "plain-credentials"
    "credentials"
    "credentials-binding"
    "ssh-slaves"
    "matrix-auth"
    "pam-auth"
    "ldap"
    "email-ext"
    "mailer"
    "slack"
    "discord-notifier"
    "telegram-notifications"
)

# Instalar cada plugin
for plugin in "${plugins[@]}"; do
    echo "Installing plugin: $plugin"
    java -jar jenkins-cli.jar -s http://localhost:8080 install-plugin "$plugin" --username admin --password admin123
done

echo "All plugins installed successfully!"
echo "Please restart Jenkins to complete the installation."








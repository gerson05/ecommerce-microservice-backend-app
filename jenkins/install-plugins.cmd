@echo off
echo Installing Jenkins plugins...

REM Lista de plugins necesarios
set plugins=docker-workflow kubernetes kubernetes-cli kubernetes-client-api pipeline-stage-view workflow-aggregator git maven-plugin htmlpublisher junit email-ext build-timeout credentials-binding timestamper ws-cleanup ant gradle pipeline-github-lib pipeline-stage-view pipeline-graph-analysis pipeline-input-step pipeline-milestone-step pipeline-model-definition pipeline-rest-api pipeline-stage-tags-metadata pipeline-utility-steps ssh-slaves matrix-auth pam-auth ldap email-ext mailer slack discord-notifier telegram-notifications matrix-project resource-disposer ssh-credentials plain-credentials credentials credentials-binding ssh-slaves matrix-auth pam-auth ldap email-ext mailer slack discord-notifier telegram-notifications

REM Instalar cada plugin
for %%p in (%plugins%) do (
    echo Installing plugin: %%p
    java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 install-plugin "%%p" -deploy
)

echo All plugins installed successfully!
echo Please restart Jenkins to complete the installation.




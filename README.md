# knt-lib-generic1
knt-lib-generic1

## Instructions 
Execute following command for CUI passpharase setting before every release
gpg --use-agent --armor --detach-sign --output $(mktemp) pom.xml
Passphrase: tech.koolnano.generic1

## Important commands:
1.   clear && mvn clean deploy -P release
2.  gpg --list-secret-keys
3. MacOS: reset && mvn test -Dtest=TestSecurityHelper1#test_CodeDe1

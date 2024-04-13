const scanner = require('sonarqube-scanner');

scanner(
  {
    serverUrl: 'http://localhost:9000',
    token: 'sqp_fc05fe0c3cef6c68c0bf21b8d4cee6ab68ed8d83',
    options: {
      'sonar.projectKey': 'SIRESP-FRONT',
      'sonar.sources': '.',
    },
  },
  () => process.exit()
);
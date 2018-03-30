node {
   def mvnHome
   stage('Checkout') {
        git branch: 'master',  url: 'https://github.com/midianet/journey-adm'
   }
   try{
      stage 'Build'
      mvnHome = tool 'M3'       
      sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore -Dorg.slf4j.simpleLogger.defaultLogLevel=INFO  clean package"
    }catch(Exception e){
        failBuild()
    }   
}

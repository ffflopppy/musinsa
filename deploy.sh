

echo "cd musinsa > 프로젝트 Build 시작"
cd musinsa
./gradlew build


echo "> Build 파일 복사"
cp ./musinsa/build/libs/*.jar

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f musinsa)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -2 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"
nohup java -jar ./build/libs/musinsa-0.0.1-SNAPSHOT.jar

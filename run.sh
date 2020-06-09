#!/bin/bash
# Source function library.
# import some useful functions : status(), echo_success(), echo_failed() ....
#. /opt/vlive/bin/vlive_functions

RETVAL=0
FOLDER=$HOME"/appNhaTro/auth"
echo $FOLDER
MODULE_NAME="auth-0.1"
echo $MODULE_NAME
CONFIG_FOLDER="$FOLDER/config"
echo $CONFIG_FOLDER
CONFIG_FILE="application.yml"
echo $CONFIG_FILE
JAVA_OPTS="-Xmx256m -Xms256m -Dfile.encoding=UTF8 -Dspring.config.location=$CONFIG_FOLDER/$CONFIG_FILE"
echo $JAVA_OPTS
JAR_FILE="$MODULE_NAME.jar"
echo $JAR_FILE
MODULE_OPTS=""
echo $MODULE_OPTS
start() {
    local PID=`ps -ef | grep java | grep $MODULE_NAME | grep -v controller_user | grep -v grep | awk '{print $2}'`
    if [ -z "$PID" ]
    then
        cd $FOLDER
        java $JAVA_OPTS -jar $JAR_FILE $MODULE_OPTS > /dev/null 2>&1 &
	else
        echo "$MODULE_NAME is running(pid=$PID)..."
    fi
	echo "success"
}

stop() {
    local PID=`ps -ef | grep java | grep $MODULE_NAME | grep -v controller_user | grep -v grep | awk '{print $2}'`

    if [ -z "$PID" ]
    then
        echo "$MODULE_NAME not started"
        echo ""
        return 1
    fi

    echo "Killing $MODULE_NAME(pid=$PID)"
    echo ""
    kill -9 ${PID}
    RETVAL=$?
    if [ $RETVAL -eq 0 ]
    then
        echo "Killed $MODULE_NAME"
        echo ""
    else
        echo "Failed to kill $MODULE_NAME"
        echo ""
    fi

    return $RETVAL
}

reload() {
    local app_idx=${1##*/}
    local app_name=${2##*/}
    local action_name=${3##*/}
    local module_name=${4##*/}
    local sub_module_name=${5##*/}

    if  [ -z $module_name ]
    then
        help "reload"
        exit 0
    fi

    if  [ -z $module_name ]
    then
        help "reload"
        exit 0
    fi

    case $module_name in
    *)
         controller user RELOAD_CONFIG $app_name$app_idx $sub_module_name
         ;;
    esac
}

view() {
    local app_idx=${1##*/}
    local app_name=${2##*/}
    local action_name=${3##*/}
    local module_name=${4##*/}
    local sub_module_name=${5##*/}
    local tag=${6##*/}

    if  [ -z $module_name ]
    then
        help "view"
        exit 0
    fi

    if  [ -z $module_name ]
    then
        help "view"
        exit 0
    fi

    case $module_name in
    *)
        controller user GET_MODULE_INFO $app_name$app_idx $sub_module_name $tag
        ;;
    esac
}

status() {
    local app_idx=${1##*/}
    local app_name=${2##*/}
    #local PID=`jps -m | grep $app_name$app_idx | grep -v controller | grep -v grep | awk '{print $1}'`
    #local PID=`ps -ef | grep $app_name$app_idx | grep -v controller | grep -v grep | awk '{print $2}'`
    local PID=`ps -ef | grep java | grep $app_name$app_idx | grep -v controller_user | grep -v grep | awk '{print $2}'`

    if [ -z "$PID" ]
    then
        echo "$app_name$app_idx is not running"
    else
        echo "$app_name$app_idx is running(pid=$PID)..."
    fi

    echo ""
    echo ""
}

restart() {
    echo "----------------------------------"
    stop $1 $2

    sleep 3
    echo "----------------------------------"
    echo "Starting new instance of $2$1"
    start $1 $2
    status $1 $2
}

help_reload(){
    return 0
}

help_view(){
    return 0
}

usage(){
    local shell_name=${0##*/}
    local app_idx=${1##*/}
    local app_name=${2##*/}
    local action_name=${3##*/}
    local module_name=${4##*/}
    local sub_module_name=${5##*/}

    echo $"Usage: fp_servicecore {start|stop|restart|reload|view|status|help}"
}

help(){
    usage
}

list_action(){
    echo "start|stop|restart|reload|view|status|help"
}

action_name=${1##*/}
case $action_name in
start)
    start $1 $2
    ;;

stop)
    stop $1 $2
    ;;

reload)
    reload $1 $2 $3 $4 $5
    ;;

restart)
    restart $1 $2
    ;;

view)
    view $1 $2 $3 $4 $5 $6
    ;;

status)
    status $1 $2
    ;;

help)
    help $1 $2 $3 $4
    ;;
*)
    usage
    exit 1
esac

exit $?
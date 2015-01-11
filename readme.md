#System Requirements
Oracle JDK 1.7 or higher  
**linux-utilites:**
* wget
* xsel

#Usage
Сделано по мотивам [этого поста](http://habrahabr.ru/post/137215/).
Главное отличие -- возможность переводить абзацы (за счёт кодирования вывода xsel с помощью `URLEncoder.encode()`).
Чтобы начать пользоваться этой утилитой, нужно:  
* забиндить в системе сочетание клавиш (например Alt+A) на запуск `java -jar workspace/TranslateNotify/target/translateNotify.jar`  
* выделить текст  
* нажать свежесозданный хоткей.  

#Скриншот
![Скриншот](https://img-fotki.yandex.ru/get/15527/165433899.1/0_13459c_5ea134eb_orig)

#Features
* запросы к JSON через JSONPath  
* создан собственный [артифакт Executor](https://github.com/nikit-cpp/Executor.git) для удобного получения stdout, stderr и exitcode процесса.  
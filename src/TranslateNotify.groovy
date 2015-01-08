@Grab(group='com.jayway.jsonpath', module='json-path', version='1.2.0')
import com.jayway.jsonpath.JsonPath;
class ExecutorResult{
	List<String> procOut
	List<String> procErr
	int exitCode
}

ExecutorResult procLaunch(def launch){
	// Определяем наличие vboxwebsrv
	Process processList = launch.execute()
	BufferedReader inReader = new BufferedReader(new InputStreamReader(processList.inputStream));
	BufferedReader errReader = new BufferedReader(new InputStreamReader(processList.errorStream));
	int exitCode = processList.waitFor();
	String tmp;
	
	List<String> procOut = new ArrayList<String>();
	while ((tmp=inReader.readLine())!=null) {
		procOut.add(tmp)
	}
	
	List<String> procErr = new ArrayList<String>();
	while ((tmp=errReader.readLine())!=null) {
		procErr.add(tmp)
	}
	
	inReader.close();
	errReader.close();
	return new ExecutorResult(procOut:procOut, procErr:procErr, exitCode:exitCode)
}


String psString = "xsel -o"
ExecutorResult xselResult = procLaunch(psString)
// Вызов xsel -o
// Получение stdout в строку
// Кодирование строки в url
String xselOutput = xselResult.procOut.join("\n")
println "From xsel input:"
println xselOutput

// http://stackoverflow.com/questions/10334358/how-to-get-and-parse-json-answer-from-google-translate/10527235#10527235
String url = "http://example.com/query?q=" + URLEncoder.encode('random word £500 bank $.\nHi, america!', 'UTF-8');
// wget -U "Mozilla/5.0" -qO - "http://translate.google.com/translate_a/t?client=t&text="cat"&sl=auto&tl=ru"
List wget = [
"wget",
"-U",
"Mozilla/5.0",
"-qO",
"-",
"http://translate.google.com/translate_a/t?client=p&text=\"${xselOutput}\"&sl=auto&tl=ru"
]
//println wget.join(" ")
def wgetResult = procLaunch(wget)
List json = wgetResult.procOut
strjson = json.join("")
println "JSON:"
println strjson


/*
But in case if you get arbitrary JSON objects and you would like process them in the way you described, I would suggest combine Jackson JSON processor along with Apache's Commons Beanutils.
The idea is the following: Jackson by default process all JSON's as java.util.Map instances, meanwhile Commons Beanutils simplifies property access for objects, including arrays and Map supports.
 */

def strings = JsonPath.read(strjson, 'sentences[*].trans');
strings.each{
	println it
}
//println strings
// Запрос wget'ом закодированной строки c google-translate

// Разбор результата

// Передача исходного текста и результата в notify-send
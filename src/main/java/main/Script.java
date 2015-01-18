package main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.github.nikit.cpp.executor.ExecuteException;
import com.github.nikit.cpp.executor.Executor;
import com.github.nikit.cpp.executor.ExecutorResult;
import com.jayway.jsonpath.JsonPath;


public class Script {

	public static void main(String[] args) throws ExecuteException, UnsupportedEncodingException {
		String[] xsel = new String[]{
				"xsel",
				"-o"
		};
		// Вызов xsel -o
		ExecutorResult xselResult = Executor.execute(xsel);
		String xselOutput = xselResult.getStdOut();
		System.out.println("Selected text:");
		System.out.println(xselOutput);
		
		// Кодирование строки в url для возможности перевода нескольких абзацев -- кодируем энтеры, запятые и прочие точки.
		String encoded = URLEncoder.encode(xselOutput, "UTF-8");
		System.out.println("Encoded:");
		System.out.println(encoded);
		
		String url= "http://translate.google.com/translate_a/t?client=p&text=\"" + encoded + "\"&sl=auto&tl=ru";
		// Запрос wget'ом закодированной строки c google-translate
		String[] wget = new String[]{
			"wget",
			"-U",
			"Mozilla/5.0",
			"-qO",
			"-",
			url
		};
		//println wget.join(" ")
		ExecutorResult wgetResult = Executor.execute(wget);
		
		String rawJson = wgetResult.getStdOut();
		// Удаляем лишние слэш и кавычку
		String json=rawJson.replace("\\\"", "");
		System.out.println("JSON responce:");
		System.out.println(json);
		
		
		/*
		But in case if you get arbitrary JSON objects.
		And you would like process them in the way you described, I would suggest combine Jackson JSON processor along with Apache's Commons Beanutils.
		The idea is the following: Jackson by default process all JSON's as java.util.Map instances.
		Meanwhile Commons Beanutils simplifies property access for objects, including arrays and Map supports.
		 */
		
		List<String> strings = JsonPath.read(json, "$.sentences[*].trans");
		String translateResult = StringUtils.join(strings, "");
		System.out.println(translateResult);
		
		// Передача исходного текста и результата в notify-send
		String[] notify = new String[]{
				"notify-send",
				"-u",
				"critical",
				xselOutput,
				translateResult
		};
		Executor.execute(notify);
	}

}

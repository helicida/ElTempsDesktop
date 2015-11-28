package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by sergi on 8/11/15.
 */

public class Parser {

    public static String nombreCiudad;
    public static ArrayList<String> dias = new ArrayList<>();
    public static ArrayList<String> temperatura = new ArrayList();
    public static ArrayList<String> temperaturaMax = new ArrayList();
    public static ArrayList<String> temperaturaMin = new ArrayList();
    public static ArrayList<String> prevision = new ArrayList();
    public static ArrayList<String> humedad = new ArrayList();
    public static final File XML = new File("forecast.xml");

    public static String getNombreCiudad() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();   //Hacemos el documentBuilder
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(XML);

        nombreCiudad = doc.getElementsByTagName("name").item(0).getTextContent();   //Buscamos el nombre de la ciudad y lo devolvemos
        return nombreCiudad;
    }

    public static void anadirInfoArrays() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();  //Hacemos els documentBuilders
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(XML);
        doc.getDocumentElement().normalize();    //Normalitzamos el archivo

        NodeList nList = doc.getElementsByTagName("time");  //Listamos todos los elementos que pertenezcan a time

        for (int temp = 0; temp < nList.getLength(); temp++) {  //For con el que iteraremos a través de todos los elementos
            Element elementos = (Element) nList.item(temp);
            dias.add(elementos.getAttributes().getNamedItem("day").getNodeValue());
            prevision.add(elementos.getElementsByTagName("clouds").item(0).getAttributes().getNamedItem("value").getNodeValue());
            humedad.add(elementos.getElementsByTagName("humidity").item(0).getAttributes().getNamedItem("value").getNodeValue() + "%");
            temperatura.add(elementos.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("day").getNodeValue() + "º");
            temperaturaMin.add(elementos.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("min").getNodeValue() + "º");
            temperaturaMax.add(elementos.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("max").getNodeValue() + "º");
        }
    }

    public static String toString(int pos) {
        return  "Dia = " + dias.get(pos) + "\n" +
                "Previsión: " + prevision.get(pos) + "\n" +
                "Humedad: " + humedad.get(pos) + "\n" +
                "Temperatura = " + temperatura.get(pos) + "\n" +
                "TemperaturaMax = " + temperaturaMax.get(pos) +"\n" +
                "TemperaturaMin = " + temperaturaMin.get(pos);
    }

    public static String mitjana(int semanas){

        DecimalFormat dosDecimales = new DecimalFormat("#.##");

        semanas = semanas * 7;

        double mediaTemperatura = 0;
        double mediaHumedad = 0;
        double mediaTemperaturaMax = 0;
        double mediaTemperaturaMin = 0;

        for(int iterador = 0; iterador < semanas; iterador++){
            mediaTemperatura = mediaTemperatura + Double.parseDouble(temperatura.get(iterador).replace('º', ' '));
            mediaHumedad = mediaHumedad + Double.parseDouble(humedad.get(iterador).replace('%', ' '));
            mediaTemperaturaMax = mediaTemperaturaMax + Double.parseDouble(temperaturaMax.get(iterador).replace('º', ' '));
            mediaTemperaturaMin = mediaTemperaturaMin + Double.parseDouble(temperaturaMin.get(iterador).replace('º', ' '));
        }

        mediaTemperatura = mediaTemperaturaMax / semanas;
        mediaHumedad = mediaHumedad / semanas;
        mediaTemperaturaMax = mediaTemperaturaMax / semanas;
        mediaTemperaturaMin = mediaTemperaturaMin / semanas;

        return "Estadisticas: \n" +
                "------------------------------- \n" +
                "Temperatura = " + dosDecimales.format(mediaTemperatura) + " º \n" +
                "Huemdad = " + dosDecimales.format(mediaHumedad) + " % \n" +
                "TemperaturaMax = " + dosDecimales.format(mediaTemperaturaMax) + " º \n" +
                "TemperaturaMin = " + dosDecimales.format(mediaTemperaturaMin) + " º";
    }

    public static String toPrevision(int pos){
        return prevision.get(pos);
    }
}

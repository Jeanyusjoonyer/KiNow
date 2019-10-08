package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.v1.FirestoreAdminClient;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.internal.NonNull;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {
   static Firestore db;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
    try
    {
      FileInputStream serviceAccount =
          new FileInputStream("C:/Users/A704600/Documents/serviceAccountKey.json");

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://kinow-46514.firebaseio.com")
          .build();

      FirebaseApp.initializeApp(options);
    }catch(Exception e){}

     db = FirestoreClient.getFirestore();
  }

  @RestController
  public static class SimpleController {

    @RequestMapping(value = "/Person")
    public ResponseEntity<Nutzer> getPerson() {
      return new ResponseEntity<Nutzer>(new Nutzer(1,"Hans","peter",new Date(),"email@email.com","1234"), HttpStatus.OK);
    }

    @RequestMapping(value = "/Kino")
    public ResponseEntity<Object> getKino(@RequestHeader("kinoID") int kino) {
      Kino k = new Kino(kino, "HAns", "Pad");
      Map<Integer, Kino> KinoRepo = new HashMap<>();
      KinoRepo.put(k.getKinoID(), k);
      return new ResponseEntity<>(KinoRepo.values(), HttpStatus.OK);
    }

    @RequestMapping(value = "/KinoBody")
    public ResponseEntity<Object> getKino(@RequestBody() String kino) {
      Gson gson = new Gson();
      Map<Integer, Kino> KinoRepo = new HashMap<>();
      try {
        Kino kino1 = gson.fromJson(kino, Kino.class);
        KinoRepo.put(kino1.getKinoID(), kino1);
        return new ResponseEntity<>(KinoRepo.values(), HttpStatus.OK);
      } catch (Exception e) {
      }
      return new ResponseEntity<>(KinoRepo.values(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/setNutzer")
    public void setData() {
    Nutzer n= new Nutzer(10,"Peter","Hans",new Date(),"Waveboardlukas@Meer.de","1234");
      Gson gson = new Gson();
      DocumentReference docRef = db.collection("Nutzer").document();
      //Nutzer nutzer=gson.fromJson(body,Nutzer.class);
      ApiFuture<WriteResult> result = docRef.set(n);
      System.out.println(result.toString());
    }

    @RequestMapping(value = "/getAllData")
    public ResponseEntity<Object> getAllData(@RequestHeader ("head") String head) {
      // asynchronously retrieve all users
      ApiFuture<QuerySnapshot> query = db.collection(head).get();
      Map<Integer, Object> data = new HashMap<>();
// ...
// query.get() blocks on response
      try{
      QuerySnapshot querySnapshot = query.get();
      List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
      for (QueryDocumentSnapshot document : documents) {
        data.put(Integer.parseInt(document.getId()),document.getData());
      }}catch(Exception e){}
      return new ResponseEntity<>(data.values(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/getNutzerbyName")
    public ResponseEntity<ArrayList> getNutzer(@RequestHeader ("name") String name) {
      // asynchronously retrieve all users
      Query query = db.collection("Nutzer").whereEqualTo("nachname",name);
      ApiFuture<QuerySnapshot> querySnapshot = query.get();
      ArrayList<Nutzer> list= new ArrayList<>();
      try {
        Nutzer nutzer=new Nutzer();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
           nutzer= document.toObject(Nutzer.class);
           list.add(nutzer);
        }
// ...
// query.get() blocks on response
        return new ResponseEntity<ArrayList>(list, HttpStatus.ACCEPTED);
      }catch(Exception e){}
      return new ResponseEntity<ArrayList>(list, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getNutzerbyID")
    public ResponseEntity<ArrayList> getNutzerbyID(@RequestHeader ("id") int id) {
      // asynchronously retrieve all users
      Query query = db.collection("Nutzer").whereEqualTo("nid",id);
      ApiFuture<QuerySnapshot> querySnapshot = query.get();
      ArrayList<Nutzer> list= new ArrayList<>();
      try {
        Nutzer nutzer=new Nutzer();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
          nutzer= document.toObject(Nutzer.class);
          list.add(nutzer);
        }
// ...
// query.get() blocks on response
        System.out.println("Anfrage von id:" +id);
        return new ResponseEntity<ArrayList>(list, HttpStatus.ACCEPTED);
      }catch(Exception e){}
      return new ResponseEntity<ArrayList>(list, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/getFilmInKino")
    public ResponseEntity <ArrayList> getFilmInKino(@RequestHeader ("kino") String kino){
      // asynchronously retrieve all users
      Query query = db.collection("Filme").whereEqualTo("nachname",kino);
      ApiFuture<QuerySnapshot> querySnapshot = query.get();
      ArrayList<Nutzer> list= new ArrayList<>();
      try {
        Nutzer nutzer=new Nutzer();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
          nutzer= document.toObject(Nutzer.class);
          list.add(nutzer);
        }
// ...
// query.get() blocks on response
        return new ResponseEntity<ArrayList>(list, HttpStatus.ACCEPTED);
      }catch(Exception e){}
      return new ResponseEntity<ArrayList>(list, HttpStatus.BAD_REQUEST);

    }
  }




}

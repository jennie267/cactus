
## Cactus 메세지 API    
* **URL**    

   http://messagecactus.paas-ta.org/message/

### 부모 별 메세지 검색

---
* **URL**

  receive/user/:id

* **Method:**   

   `GET`
   
### 부모, 자식 별 메세지 검색

---
* **URL**

  send/user/:received_user_id/:send_user_id

* **Method:**   

   `GET`
   
### 가장 최근 메세지 검색

---
* **URL**

  first/:received_user_id/:send_user_id

* **Method:**   

   `GET`
   
### 메세지 입력

---
* **URL**

  

* **Method:**   

   `POST`
   

## Cactus 푸시 API    
* **URL**    

   http://messagecactus.paas-ta.org/push/

### 보낸 데이터의 푸시 메세지 검색

---
* **URL**

  type/:sendType/id/:sendId

* **Method:**   

   `GET`
      

### 사용자의 푸시 검색

---
* **URL**

  user/:id

* **Method:**   

   `GET`
      
### 푸시 보내기

---
* **URL**

  

* **Method:**   

   `POST`
   
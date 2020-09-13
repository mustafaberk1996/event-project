Kotlin ile geliştirilmiştir.

Kullanılan Kütüphanler:

Retrofit
Gson
Glide
Circleimageview
Realm

classlar ve metotlar burada açıklanacaktır.

getEventList()
----------------------------------
Internet kontrolü yaparak ilgili dataların local database üzerinden yada API üzerinden getirileceğine karar verir

getDataFromServer()
----------------------------------
API üzerinden veri transferi yapar

getDataFromLocal()
----------------------------------
Realm database'de bulunan dataları döndürür

saveLocalDatabase()
----------------------------------
API üzerinden dönen data list'i local database'e kaydeder, öncesinde var olan datayı siler

fun showDataOnList(eventList: MutableList<Event>, totalDataCount: Int) 
-------------------------------------------------------------------------
Elde edilen data list'i ekran üzerinde gösterir ve sayfa numaralarını oluşturur

EventListItemClickListener
----------------------------
fun onItemClick(event: Event)
----------------------------
Liste üzerindeki Event datalarına dokunma event'ını ele alan ClickListener. onItemClick metodu parametre olarak Event tipinde bir nesne alır

PaginationItemClickListener
----------------------------
fun itemClick(position: Int)
----------------------------
Sayfa numaralarına tıklanılma durumunu ele alan ClickListener. itemClick metodu parametre olarak tıklanılan pozisyonun index değerini alır


Base
------
API üzerinden dönen değere karşılık gelen Class yapısı.

Event
------
Event modellerine karşılık gelen Class yapısı.

Guest
------
Guest modellerine karşılık gelen Class yapısı.

EventService
-------------
İçerisinde API istek metotlarının tanımlandığı interface yapısı.

ApiClient
----------
Tek bir retrofit objesi oluşturmak için hazırlanan Class yapısı. (Singleton Pattern)

Connectivity
-------------
Internet bağlantı kontrollerinin sağlandığı Class yapısı.

Constants
-----------
Proje içerisindeki çeşitli sabit değerlerin tanımlandığı Class yapısı.
 








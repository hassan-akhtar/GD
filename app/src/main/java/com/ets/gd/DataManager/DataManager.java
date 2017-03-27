package com.ets.gd.DataManager;

import com.ets.gd.Models.Asset;
import com.ets.gd.Models.InspectionDates;
import com.ets.gd.Models.Location;
import com.ets.gd.Models.Note;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class DataManager {

    private static DataManager dataManager;
    static Realm realm;



    public static DataManager getInstance() {
        if (dataManager == null)
        {
            dataManager = new DataManager();
            realm = Realm.getDefaultInstance();
        }
        return dataManager;
    }


    // For adding an asset info in DB
    public void AddAssetInfo(final Asset obj){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.createObject(Asset.class);
                asset.setTagID(obj.getTagID());
                asset.setDeviceType(obj.getDeviceType());
                asset.setManufacturers(obj.getManufacturers());
                asset.setSerialNo(obj.getSerialNo());
                asset.setVendor(obj.getVendor());
                asset.setAgent(obj.getAgent());
            }
        });
    }




//    // For adding an asset location in DB
//    void AddAssetLocation( final String tagID, final Location obj){
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.where(Asset.class).contains("tagID",tagID).;
//                asset.setLocation(obj);
//            }
//        });
//    }

    public Asset getAsset(String barcodeID){
        return realm.where(Asset.class).equalTo("tagID", barcodeID).findFirst();
    }


    public Location getLocation(String barcodeID){
        return realm.where(Location.class).equalTo("locationID", barcodeID).findFirst();
    }


    // For adding an asset notes in DB
    public void AddAssetNotes(final Asset obj){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.createObject(Asset.class);
                asset.setTagID(obj.getTagID());
                asset.setDeviceType(obj.getDeviceType());
                asset.setManufacturers(obj.getManufacturers());
                asset.setSerialNo(obj.getSerialNo());
                asset.setVendor(obj.getVendor());
                asset.setAgent(obj.getAgent());
            }
        });
    }



    // For adding Asset Inspection Dates in DB
    public  void AddAssetInspectionDates(final Asset obj){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.createObject(Asset.class);
                asset.setTagID(obj.getTagID());
                asset.setDeviceType(obj.getDeviceType());
                asset.setManufacturers(obj.getManufacturers());
                asset.setSerialNo(obj.getSerialNo());
                asset.setVendor(obj.getVendor());
                asset.setAgent(obj.getAgent());
            }
        });
    }
    // For getting asset all assets from DB
    public   List<Asset> getAllAssets(){
        return realm.where(Asset.class).findAllSorted("manufacturers");

    }

    // For getting asset all assets from DB
    public   List<Asset> getAllAssets(int count){
        return realm.where(Asset.class).findAllSorted("manufacturers").subList(0,count);

    }

    // For getting asset all assets from DB
    public   List<Location> getAllLocations(){
        return realm.where(Location.class).findAllSorted("locationID");

    }



    public  void setupDataForApp(){

        // Adding dummy asset 1
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.createObject(Asset.class);
                asset.setTagID("8961006040044");
                asset.setDeviceType("EXT");
                asset.setManufacturers("Ansul");
                asset.setModel("002199");
                asset.setSerialNo("002323");
                asset.setMfgDate("2/6/2017");
                asset.setVendor("Lipsum");
                asset.setAgent("Lipsum");
                Location location = realm.createObject(Location.class);
                location.setLocationID("00416");
                location.setDescription("This is a Desc");
                location.setAgent("Lipsum");
                location.setVendor("Lipsum");
                location.setPlace("Shelf");
                asset.setLocation(location);
                RealmList<Note> list = new RealmList<Note>();
                Note note = realm.createObject(Note.class);
                note.setNoteTitle("Note 1");
                note.setNoteDescription("Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum");
                list.add(note);
                note.setNoteTitle("Note 2");
                note.setNoteDescription("Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum");
                list.add(note);
                asset.setNoteList(list);
                InspectionDates inspectionDates = realm.createObject(InspectionDates.class);
                inspectionDates.setDaily("2/2/2017");
                inspectionDates.setWeekly("2/2/2017");
                inspectionDates.setMonthly("2/2/2017");
                inspectionDates.setQuaterly("2/2/2017");
                inspectionDates.setSemiAnnual("2/2/2017");
                inspectionDates.setAnnual("2/2/2017");
                inspectionDates.setFiveYear("2/2/2017");
                inspectionDates.setSixYear("2/2/2017");
                inspectionDates.setTenYear("2/2/2017");
                inspectionDates.setTwelveYear("2/2/2017");
                asset.setInspectionDates(inspectionDates);
            }
        });

        // Adding dummy asset 2
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.createObject(Asset.class);
                asset.setTagID("6951582500720");
                asset.setDeviceType("EXT");
                asset.setManufacturers("Ansul");
                asset.setSerialNo("005555");
                asset.setModel("002216");
                asset.setMfgDate("2/2/2017");
                asset.setVendor("Lipsum");
                asset.setAgent("Lipsum");
                Location location = realm.createObject(Location.class);
                location.setLocationID("00416");
                location.setDescription("This is a Desc");
                location.setAgent("Lipsum");
                location.setVendor("Lipsum");
                location.setPlace("Shelf");
                asset.setLocation(location);
                RealmList<Note> list = new RealmList<Note>();
                Note note = realm.createObject(Note.class);
                note.setNoteTitle("Note 1");
                note.setNoteDescription("Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum");
                list.add(note);
                note.setNoteTitle("Note 2");
                note.setNoteDescription("Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum");
                note.setNoteTitle("Note 3");
                note.setNoteDescription("Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum");
                note.setNoteTitle("Note 4");
                note.setNoteDescription("Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum");
                list.add(note);
                asset.setNoteList(list);
                InspectionDates inspectionDates = realm.createObject(InspectionDates.class);
                inspectionDates.setDaily("2/2/2017");
                inspectionDates.setWeekly("2/2/2017");
                inspectionDates.setMonthly("2/2/2017");
                inspectionDates.setQuaterly("2/2/2017");
                inspectionDates.setSemiAnnual("2/2/2017");
                inspectionDates.setAnnual("2/2/2017");
                inspectionDates.setFiveYear("2/2/2017");
                inspectionDates.setSixYear("2/2/2017");
                inspectionDates.setTenYear("2/2/2017");
                inspectionDates.setTwelveYear("2/2/2017");
                asset.setInspectionDates(inspectionDates);
            }
        });


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Location location = realm.createObject(Location.class);
                location.setLocationID("8961006040044");
                location.setDescription("This is a Desc");
                location.setAgent("Lipsum");
                location.setVendor("Lipsum");
                location.setPlace("Shelf");
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Location location = realm.createObject(Location.class);
                location.setLocationID("87126228");
                location.setDescription("This is a Desc");
                location.setAgent("Lipsum");
                location.setVendor("Lipsum");
                location.setPlace("Shelf");
            }
        });


    }

}

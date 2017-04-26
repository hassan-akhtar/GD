package com.ets.gd.DataManager;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.InspectionDates;
import com.ets.gd.Models.Location;
import com.ets.gd.Models.Note;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.Models.RouteLocation;
import com.ets.gd.Models.Routes;
import com.ets.gd.NetworkLayer.ResponseDTOs.AgentType;
import com.ets.gd.NetworkLayer.ResponseDTOs.Building;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer;
import com.ets.gd.NetworkLayer.ResponseDTOs.Model;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyInspectionDates;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.Site;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DataManager {

    private static DataManager dataManager;
    static Realm realm;


    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
            realm = Realm.getDefaultInstance();
        }
        return dataManager;
    }



    public static Realm getRealm(){
        return realm;
    }


    public void deleteRealm(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
             realm.deleteAll();
            }
        });
    }

    // For adding an asset info in DB
    public void AddAssetInfo(final Asset obj) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.createObject(Asset.class);
                asset.setTagID(obj.getTagID());
                asset.setModel(obj.getModel());
                asset.setMfgDate(obj.getMfgDate());
                asset.setDeviceType(obj.getDeviceType());
                asset.setManufacturers(obj.getManufacturers());
                asset.setSerialNo(obj.getSerialNo());
                asset.setVendor(obj.getVendor());
                asset.setAgent(obj.getAgent());
            }
        });
    }


    // For adding an asset info in DB
    public void updateAssetLocationID(final List<FireBugEquipment> assetList, final String newLocId) {

        for (FireBugEquipment asset : assetList) {
            realm.beginTransaction();
            FireBugEquipment assett = realm.where(FireBugEquipment.class).equalTo("ID", asset.getID()).findFirst();
            MyLocation myLocation = realm.where(MyLocation.class).equalTo("ID", Integer.parseInt(newLocId)).findFirst();
            assett.setLocation(myLocation);
            realm.commitTransaction();

        }

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

    public Asset getAsset(String barcodeID) {
        return realm.where(Asset.class).equalTo("tagID", barcodeID).findFirst();
    }

    public boolean doesAssetExist(String barcodeID) {
        Asset asset = realm.where(Asset.class).equalTo("tagID", barcodeID).findFirst();

        if (null != asset)
            return true;
        else
            return false;
    }

    public void addUpdateAssetLocation(final String barcodeID, final Location loc) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.where(Asset.class).equalTo("tagID", barcodeID).findFirst();
                Location location = realm.createObject(Location.class);
                location.setLocationID(loc.getLocationID());
                location.setDescription(loc.getDescription());
                location.setAgent(loc.getAgent());
                location.setVendor(loc.getVendor());
                location.setPlace(loc.getPlace());
                asset.setLocation(location);
            }
        });

    }

    public void addUpdateAssetNote(final int equipmentID, final int customerID, final List<EquipmentNote> noteList) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmSyncGetResponseDTO realmSyncGetResponse = realm.where(RealmSyncGetResponseDTO.class).equalTo("CustomerId", customerID).findFirst();
                RealmResults<EquipmentNote> oldList = realm.where(EquipmentNote.class).findAll();
                RealmList<EquipmentNote> res = new RealmList<EquipmentNote>();
                res.addAll(oldList);
                RealmList<EquipmentNote> newItems = new RealmList<EquipmentNote>();

//                for (int i = 0; i < noteList.size(); i++) {
//                    for (int j = 0; j < res.size(); j++) {
//                        if (!res.get(j).getNote().equals(noteList.get(i).getNote())) {
//                            EquipmentNote equipmentNote =realm.createObject(EquipmentNote.class);
//                            equipmentNote.setNote(noteList.get(i).getNote());
//                            equipmentNote.setEquipmentID(noteList.get(i).getEquipmentID());
//                            equipmentNote.setModifiedTime(noteList.get(i).getModifiedTime());
//                            equipmentNote.setModifiedBy(noteList.get(i).getModifiedBy());
//                            newItems.add(equipmentNote);
//                            break;
//
//                        } else if (res.get(j).getNote().equals(noteList.get(i).getNote()) && res.get(j).getEquipmentID() != noteList.get(i).getEquipmentID()) {
//                            EquipmentNote equipmentNote =realm.createObject(EquipmentNote.class);
//                            equipmentNote.setNote(noteList.get(i).getNote());
//                            equipmentNote.setEquipmentID(noteList.get(i).getID());
//                            equipmentNote.setModifiedBy(noteList.get(i).getModifiedTime());
//                            newItems.add(equipmentNote);
//                            break;
//                        } else {
//
//                        }
//                    }
//
//                }

                for (int i = 0; i < noteList.size(); i++) {
                            EquipmentNote equipmentNote = realm.createObject(EquipmentNote.class);
                            equipmentNote.setNote(noteList.get(i).getNote());
                            equipmentNote.setEquipmentID(noteList.get(i).getEquipmentID());
                            equipmentNote.setModifiedTime(noteList.get(i).getModifiedTime());
                            equipmentNote.setModifiedBy(noteList.get(i).getModifiedBy());
                            newItems.add(equipmentNote);
                            break;
                }

                if (0 != newItems.size()) {
                    FireBugEquipment fireBugEquipment = realm.where(FireBugEquipment.class).equalTo("ID", noteList.get(0).getEquipmentID()).findFirst();
                    res.addAll(newItems);
                    fireBugEquipment.setUpdated(true);
                    realmSyncGetResponse.setLstFbEquipmentNotes(res);
                }

                ViewAssetInformationActivity.newNotesList.clear();

            }
        });

    }

    public void addUpdateAssetInspecDates(final String barcodeID, final InspectionDates inspectionNewDates) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FireBugEquipment fireBugEquipment = realm.where(FireBugEquipment.class).equalTo("Code", barcodeID).findFirst();
                RealmResults<MyInspectionDates> results = realm.where(MyInspectionDates.class).equalTo("EquipmentID", fireBugEquipment.getID()).findAll();
                results.get(0).setDueDate(inspectionNewDates.getDaily());
                results.get(1).setDueDate(inspectionNewDates.getWeekly());
                results.get(2).setDueDate(inspectionNewDates.getMonthly());
                results.get(3).setDueDate(inspectionNewDates.getQuaterly());
                results.get(4).setDueDate(inspectionNewDates.getSemiAnnual());
                results.get(5).setDueDate(inspectionNewDates.getAnnual());
                results.get(6).setDueDate(inspectionNewDates.getFiveYear());
                results.get(7).setDueDate(inspectionNewDates.getSixYear());
                results.get(8).setDueDate(inspectionNewDates.getTenYear());
                results.get(9).setDueDate(inspectionNewDates.getTwelveYear());
                RealmList<MyInspectionDates> res = new RealmList<MyInspectionDates>();
                res.addAll(results);
                fireBugEquipment.setInspectionDates(res);
                fireBugEquipment.setUpdated(true);
            }
        });

    }


    public boolean doesLocationExist(String barcodeID) {
        Location location = realm.where(Location.class).equalTo("locationID", barcodeID).findFirst();

        if (null != location)
            return true;
        else
            return false;
    }

    public Locations getLocation(String barcodeID) {
        return realm.where(Locations.class).equalTo("Code", barcodeID).findFirst();
    }

    public Site getLocationSite(String barcodeID) {
        return realm.where(Site.class).equalTo("Code", barcodeID).findFirst();
    }

    public Building getLocationBuilding(String barcodeID) {
        return realm.where(Building.class).equalTo("Code", barcodeID).findFirst();
    }


    public void addNewLocation(Locations location) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(location);
        realm.commitTransaction();
    }


    public List<Locations> getAllAddedLocations() {
        return realm.where(Locations.class).equalTo("isAdded", true).findAll();
    }



    public void addLocation(final Location obj) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Location location = realm.createObject(Location.class);
                location.setLocationID(obj.getLocationID());
                location.setDescription(obj.getDescription());
                location.setAgent(obj.getAgent());
                location.setVendor(obj.getVendor());
                location.setPlace(obj.getPlace());
            }
        });
    }

    // For adding an asset notes in DB
    public void AddAssetNotes(final Asset obj) {

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
    public void AddAssetInspectionDates(final Asset obj) {

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
    public List<FireBugEquipment> getAllAssets() {
        return realm.where(FireBugEquipment.class).findAll();
    }

    public List<FireBugEquipment> getAllAddAssets() {
        return realm.where(FireBugEquipment.class).equalTo("isAdded", true).findAll();
    }


    public List<FireBugEquipment> getAllUpdateAssets() {
        return realm.where(FireBugEquipment.class).equalTo("isUpdated", true).findAll();
    }




    // For getting asset all assets from DB
    public Manufacturer getAssetManufacturer(String Code) {
        return realm.where(Manufacturer.class).equalTo("Code", Code).findFirst();
    }

    // For getting asset all assets from DB
    public DeviceType getAssetDeviceType(String Code) {
        return realm.where(DeviceType.class).equalTo("Code", Code).findFirst();
    }

    // For getting asset all assets from DB
    public MyLocation getAssetLocation(String Code) {
        return realm.where(MyLocation.class).equalTo("Code", Code).findFirst();
    }

    // For getting asset all assets from DB
    public Customer getAssetCustomer(int id) {
        return realm.where(Customer.class).equalTo("ID", id).findFirst();
    }

    // For getting asset all assets from DB
    public AgentType getAssetAgentType(String Code) {
        return realm.where(AgentType.class).equalTo("Code", Code).findFirst();
    }


    // For getting asset all assets from DB
    public VendorCode getAssetVendorCode(String Code) {
        return realm.where(VendorCode.class).equalTo("Code", Code).findFirst();
    }

    // For getting asset all assets from DB
    public Model getAssetModel(String Code) {
        return realm.where(Model.class).equalTo("Code", Code).findFirst();
    }


    // For getting asset all assets from DB
    public void addEquipment(FireBugEquipment fireBugEquipment) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(fireBugEquipment);
        realm.commitTransaction();
    }


    // For getting asset all assets from DB
    public List<Asset> getAllAssets(int count) {
        return realm.where(Asset.class).findAllSorted("manufacturers").subList(0, count);

    }


    public List<EquipmentNote> getAllNotes(int EquipmentID) {

        RealmResults<EquipmentNote> results = realm.where(EquipmentNote.class).equalTo("EquipmentID", EquipmentID).findAll();
        List<EquipmentNote> copied = realm.copyFromRealm(results);
        return copied;
    }

    public List<MyInspectionDates> getEquipmentInspectionDates(int EquipmentID) {

        RealmResults<MyInspectionDates> results = realm.where(MyInspectionDates.class).equalTo("EquipmentID", EquipmentID).findAll();
        List<MyInspectionDates> copied = realm.copyFromRealm(results);
        return copied;
    }


    // For getting asset all locations from DB
    public List<Locations> getAllLocations() {
        RealmResults<Locations> results = realm.where(Locations.class).findAllSorted("ID");
        List<Locations> copied = realm.copyFromRealm(results);
        return copied;
    }


    public void setupDataForApp() {
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


    public void setupSyncData() {
        // Adding dummy asset 1
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Asset asset = realm.createObject(Asset.class);
                asset.setTagID("00A111");
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
                asset.setTagID("00A222");
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
                location.setLocationID("00L111");
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
                location.setLocationID("00L222");
                location.setDescription("This is a Desc");
                location.setAgent("Lipsum");
                location.setVendor("Lipsum");
                location.setPlace("Shelf");
            }
        });


    }

    // For adding Asset Inspection Dates in DB
    public void addInspectionRoutes(final List<Routes> inspectionRoutes) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < inspectionRoutes.size(); i++) {
                    RealmList<RouteLocation> routeLocations = new RealmList<RouteLocation>();
                    Routes route = realm.createObject(Routes.class);
                    route.setId(inspectionRoutes.get(i).getId());
                    route.setCode(inspectionRoutes.get(i).getCode());
                    route.setRouteType(inspectionRoutes.get(i).getRouteType());
                    route.setCustomerID(inspectionRoutes.get(i).getCustomerID());
                    route.setDesc(inspectionRoutes.get(i).getDesc());

                    for (int j = 0; j < inspectionRoutes.get(i).getRouteLocations().size(); j++) {
                        RealmList<Asset> routeAssets = new RealmList<Asset>();
                        RouteLocation routeLocation = realm.createObject(RouteLocation.class);
                        routeLocation.setLocationID(inspectionRoutes.get(i).getRouteLocations().get(j).getLocationID());
                        routeLocation.setDescription(inspectionRoutes.get(i).getRouteLocations().get(j).getDescription());
                        routeLocation.setSite(inspectionRoutes.get(i).getRouteLocations().get(j).getSite());
                        routeLocation.setBuilding(inspectionRoutes.get(i).getRouteLocations().get(j).getBuilding());
                        routeLocation.setPlace(inspectionRoutes.get(i).getRouteLocations().get(j).getPlace());

                        for (int k = 0; k < inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().size(); k++) {
                            Asset asset = realm.createObject(Asset.class);
                            asset.setTagID(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getTagID());
                            asset.setDeviceType(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getDeviceType());
                            asset.setManufacturers(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getManufacturers());
                            asset.setSerialNo(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getSerialNo());
                            asset.setModel(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getModel());
                            asset.setMfgDate(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getMfgDate());
                            asset.setVendor(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getVendor());
                            asset.setAgent(inspectionRoutes.get(i).getRouteLocations().get(j).getRouteAssets().get(k).getAgent());
                            routeAssets.add(asset);
                        }
                        routeLocation.setRouteAssets(routeAssets);
                        routeLocations.add(routeLocation);

                    }
                    route.setRouteLocations(routeLocations);

                }
            }
        });
    }


    // For getting asset all assets from DB
    public List<Routes> getAllInspectionRoutes() {
        RealmResults<Routes> results = realm.where(Routes.class).findAllSorted("id");

        List<Routes> copied = realm.copyFromRealm(results);

        return copied;
    }

    public void saveSyncGetResponse(SyncGetResponseDTO obj) {

        RealmSyncGetResponseDTO realmSyncGetResponseDTO = new RealmSyncGetResponseDTO();
        realmSyncGetResponseDTO.setCustomerId(obj.getCustomerId());
        realmSyncGetResponseDTO.setDeviceId(obj.getDeviceId());
        realmSyncGetResponseDTO.setSyncGetTime(obj.getSyncGetTime());
        realmSyncGetResponseDTO.setLstDevices(obj.getLstDevices());
        realmSyncGetResponseDTO.setLstMusers(obj.getLstMusers());
        realmSyncGetResponseDTO.setLstFbEquipmentNotes(obj.getLstFbEquipmentNotes());
        realmSyncGetResponseDTO.setLstFbEquipments(obj.getLstFbEquipments());
        realmSyncGetResponseDTO.setLstLocations(obj.getLstLocations());
        realmSyncGetResponseDTO.setLstDeviceType(obj.getLstDeviceType());
        realmSyncGetResponseDTO.setLstManufacturers(obj.getLstManufacturers());
        realmSyncGetResponseDTO.setLstModels(obj.getLstModels());
        realmSyncGetResponseDTO.setLstVendorCodes(obj.getLstVendorCodes());
        realmSyncGetResponseDTO.setLstAgentTypes(obj.getLstAgentTypes());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmSyncGetResponseDTO);
        realm.commitTransaction();
    }


    public void saveSyncGetRealmResponse(RealmSyncGetResponseDTO obj) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
    }


    public FireBugEquipment getEquipment(String barcodeID) {
        return realm.where(FireBugEquipment.class).equalTo("Code", barcodeID).findFirst();
    }


    public RealmSyncGetResponseDTO getSyncGetResponseDTO(int ID) {
        return realm.where(RealmSyncGetResponseDTO.class).equalTo("CustomerId", ID).findFirst();
    }


}

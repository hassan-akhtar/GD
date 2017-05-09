package com.ets.gd.DataManager;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.InspectionDates;
import com.ets.gd.Models.Location;
import com.ets.gd.Models.Note;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.Models.RouteLocation;
import com.ets.gd.Models.Routes;
import com.ets.gd.NetworkLayer.RequestDTOs.InspectionStatusCodes;
import com.ets.gd.NetworkLayer.RequestDTOs.UnitinspectionResult;
import com.ets.gd.NetworkLayer.ResponseDTOs.AgentType;
import com.ets.gd.NetworkLayer.ResponseDTOs.AllCustomers;
import com.ets.gd.NetworkLayer.ResponseDTOs.Building;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType;
import com.ets.gd.NetworkLayer.ResponseDTOs.DeviceTypeStatusCodes;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer;
import com.ets.gd.NetworkLayer.ResponseDTOs.Model;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyInspectionDates;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.RegisteredDevice;
import com.ets.gd.NetworkLayer.ResponseDTOs.Site;
import com.ets.gd.NetworkLayer.ResponseDTOs.StatusCode;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncCustomer;
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


    public static Realm getRealm() {
        return realm;
    }


    public void deleteRealm() {
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
    public void updateAssetLocationID(final List<FireBugEquipment> assetList, final String newLocId, String operation, int cusID) {
        realm.beginTransaction();
        MyLocation myLocation;
        if (null != realm.where(MyLocation.class).equalTo("ID", Integer.parseInt(newLocId)).findFirst()) {
            myLocation = realm.where(MyLocation.class).equalTo("ID", Integer.parseInt(newLocId)).findFirst();
        } else {
            Locations locations = realm.where(Locations.class).equalTo("ID", Integer.parseInt(newLocId)).findFirst();
            myLocation = realm.createObject(MyLocation.class, locations.getID());
            myLocation.setCode(locations.getCode());
            myLocation.setDescription(locations.getDescription());
            if (operation.startsWith("move")) {
                myLocation.setCustomer(locations.getCustomer().getID());
            } else {
                myLocation.setCustomer(cusID);
            }

            myLocation.setSite(locations.getSite().getID());
            myLocation.setBuilding(locations.getBuilding().getID());
        }

        for (FireBugEquipment asset : assetList) {
            FireBugEquipment assett = realm.where(FireBugEquipment.class).equalTo("ID", asset.getID()).findFirst();
            if (operation.startsWith("move")) {
                assett.setMoved(true);
            } else {
                assett.setTransferred(true);
            }
            assett.setLocation(myLocation);
        }
        realm.commitTransaction();
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
                SyncCustomer realmSyncGetResponse = realm.where(SyncCustomer.class).equalTo("CustomerId", customerID).findFirst();
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
                RealmList<MyInspectionDates> res = new RealmList<MyInspectionDates>();
                if (0 != results.size()) {
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
                    res.addAll(results);
                } else {

                    MyInspectionDates myInspectionDaily = realm.createObject(MyInspectionDates.class);
                    myInspectionDaily.setID(0);
                    myInspectionDaily.setEquipmentID(fireBugEquipment.getID());
                    myInspectionDaily.setDueDate(inspectionNewDates.getDaily());
                    myInspectionDaily.setInspectionType("Daily");
                    res.add(myInspectionDaily);

                    MyInspectionDates myInspectionWeekly = realm.createObject(MyInspectionDates.class);
                    myInspectionWeekly.setID(0);
                    myInspectionWeekly.setEquipmentID(fireBugEquipment.getID());
                    myInspectionWeekly.setDueDate(inspectionNewDates.getWeekly());
                    myInspectionWeekly.setInspectionType("Weekly");
                    res.add(myInspectionWeekly);

                    MyInspectionDates myInspectionMonthly = realm.createObject(MyInspectionDates.class);
                    myInspectionMonthly.setID(0);
                    myInspectionMonthly.setEquipmentID(fireBugEquipment.getID());
                    myInspectionMonthly.setDueDate(inspectionNewDates.getMonthly());
                    myInspectionMonthly.setInspectionType("Monthly");
                    res.add(myInspectionMonthly);

                    MyInspectionDates myInspectionQuaterly = realm.createObject(MyInspectionDates.class);
                    myInspectionQuaterly.setID(0);
                    myInspectionQuaterly.setEquipmentID(fireBugEquipment.getID());
                    myInspectionQuaterly.setDueDate(inspectionNewDates.getQuaterly());
                    myInspectionQuaterly.setInspectionType("Quaterly");
                    res.add(myInspectionQuaterly);

                    MyInspectionDates myInspectionSemiAnnual = realm.createObject(MyInspectionDates.class);
                    myInspectionSemiAnnual.setID(0);
                    myInspectionSemiAnnual.setEquipmentID(fireBugEquipment.getID());
                    myInspectionSemiAnnual.setDueDate(inspectionNewDates.getSemiAnnual());
                    myInspectionSemiAnnual.setInspectionType("SemiAnnual");
                    res.add(myInspectionSemiAnnual);

                    MyInspectionDates myInspectionAnnual = realm.createObject(MyInspectionDates.class);
                    myInspectionAnnual.setID(0);
                    myInspectionAnnual.setEquipmentID(fireBugEquipment.getID());
                    myInspectionAnnual.setDueDate(inspectionNewDates.getAnnual());
                    myInspectionAnnual.setInspectionType("Annual");
                    res.add(myInspectionAnnual);

                    MyInspectionDates myInspectionFiveYear = realm.createObject(MyInspectionDates.class);
                    myInspectionFiveYear.setID(0);
                    myInspectionFiveYear.setEquipmentID(fireBugEquipment.getID());
                    myInspectionFiveYear.setDueDate(inspectionNewDates.getFiveYear());
                    myInspectionFiveYear.setInspectionType("FiveYear");
                    res.add(myInspectionFiveYear);

                    MyInspectionDates myInspectionSixYear = realm.createObject(MyInspectionDates.class);
                    myInspectionSixYear.setID(0);
                    myInspectionSixYear.setEquipmentID(fireBugEquipment.getID());
                    myInspectionSixYear.setDueDate(inspectionNewDates.getSixYear());
                    myInspectionSixYear.setInspectionType("SixYear");
                    res.add(myInspectionSixYear);

                    MyInspectionDates myInspectionTenYears = realm.createObject(MyInspectionDates.class);
                    myInspectionTenYears.setID(0);
                    myInspectionTenYears.setEquipmentID(fireBugEquipment.getID());
                    myInspectionTenYears.setDueDate(inspectionNewDates.getTenYear());
                    myInspectionTenYears.setInspectionType("TenYears");
                    res.add(myInspectionTenYears);


                    MyInspectionDates myInspectionTwelveYears = realm.createObject(MyInspectionDates.class);
                    myInspectionTwelveYears.setID(0);
                    myInspectionTwelveYears.setEquipmentID(fireBugEquipment.getID());
                    myInspectionTwelveYears.setDueDate(inspectionNewDates.getTwelveYear());
                    myInspectionTwelveYears.setInspectionType("TwelveYears");
                    res.add(myInspectionTenYears);


                }


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
        realm.copyToRealm(location);
        realm.commitTransaction();
    }

    public void addNewLocation(MyLocation location) {
        realm.beginTransaction();
        realm.copyToRealm(location);
        realm.commitTransaction();
    }


    public List<Locations> getAllAddedLocations() {
        return realm.where(Locations.class).equalTo("isAdded", true).findAll();
    }

    public List<Locations> getOldLocations() {
        return realm.where(Locations.class).equalTo("isAdded", false).findAll();
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
    public Locations getAssetLocations(String Code) {
        return realm.where(Locations.class).equalTo("Code", Code).findFirst();
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
    public List<Model> getModelFromManufacturerID(int id) {
        return realm.where(Model.class).equalTo("ManufacturerId", id).findAll();
    }



    // For getting asset all assets from DB
    public void addEquipment(FireBugEquipment fireBugEquipment) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(fireBugEquipment);
        realm.commitTransaction();
    }


    // For getting asset all assets from DB
    public Customer getCustomerByCode(String Code) {
        if (null != realm.where(Customer.class).equalTo("Code", Code).findFirst()) {
            return realm.where(Customer.class).equalTo("Code", Code).findFirst();
        } else {
            realm.beginTransaction();
            AllCustomers allCustomer = realm.where(AllCustomers.class).equalTo("Code", Code).findFirst();
            Customer customer = realm.createObject(Customer.class, allCustomer.getID());
            customer.setDescription(allCustomer.getDescription());
            customer.setCode(allCustomer.getCode());
            customer.setApplicationID(allCustomer.getApplicationID());
            customer.setServiceCompany(allCustomer.isServiceCompany());
            realm.commitTransaction();
            return customer;
        }

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

    // For getting asset all locations from DB
    public void addAllLocationstoMyLocations(List<Locations> lstLocations) {
        RealmResults<Locations> results = realm.where(Locations.class).findAllSorted("ID");

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

    public void saveSyncGetResponse(final SyncGetResponseDTO obj) {

        RealmSyncGetResponseDTO realmSyncGetResponseDTO = new RealmSyncGetResponseDTO();
        realmSyncGetResponseDTO.setID(0);
        realmSyncGetResponseDTO.setLstCustomerData(obj.getLstCustomerData());
        realmSyncGetResponseDTO.setLstCustomers(obj.getLstCustomers());
        realmSyncGetResponseDTO.setLstDeviceType(obj.getLstDeviceType());
        realmSyncGetResponseDTO.setLstManufacturers(obj.getLstManufacturers());
        realmSyncGetResponseDTO.setLstModels(obj.getLstModels());
        realmSyncGetResponseDTO.setLstVendorCodes(obj.getLstVendorCodes());
        realmSyncGetResponseDTO.setLstAgentTypes(obj.getLstAgentTypes());
        realmSyncGetResponseDTO.setLstDevices(obj.getLstDevices());


        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmSyncGetResponseDTO);
        realm.commitTransaction();
    }


    public void saveSyncGetRealmResponse(RealmSyncGetResponseDTO obj) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
    }


    public void saveUnitInspectionResults(final UnitinspectionResult inspectionResult) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UnitinspectionResult unitinspectionResult = realm.createObject(UnitinspectionResult.class);
                unitinspectionResult.setEquipmentID(inspectionResult.getEquipmentID());
                unitinspectionResult.setInspectionType(inspectionResult.getInspectionType());
                unitinspectionResult.setInspectionDate(inspectionResult.getInspectionDate());
                unitinspectionResult.setUserId(inspectionResult.getUserId());
                unitinspectionResult.setResult(inspectionResult.isResult());

                RealmList<InspectionStatusCodes> inspectionStatusCodesList = new RealmList<InspectionStatusCodes>();

                for (int i = 0; i < inspectionResult.getInspectionStatusCodes().size(); i++) {
                    InspectionStatusCodes inspectionStatusCodes = realm.createObject(InspectionStatusCodes.class);
                    inspectionStatusCodes.setStatusCodeID(inspectionResult.getInspectionStatusCodes().get(i).getStatusCodeID());
                    inspectionStatusCodesList.add(inspectionStatusCodes);
                }


                unitinspectionResult.setInspectionStatusCodes(inspectionStatusCodesList);
            }
        });

    }


    public List<UnitinspectionResult> getAllUnitInspectedAssets() {
        RealmResults<UnitinspectionResult> results = realm.where(UnitinspectionResult.class).findAll();
        List<UnitinspectionResult> copied = realm.copyFromRealm(results);
        return copied;
    }

    public StatusCode getStatusCodeID(String desc) {
        return realm.where(StatusCode.class).endsWith("Description", desc).findFirst();
    }


    public List<DeviceTypeStatusCodes> getDeviceStatusCodesList(int DeviceTypeID) {
        RealmResults<DeviceTypeStatusCodes> results = realm.where(DeviceTypeStatusCodes.class).equalTo("DeviceTypeID", DeviceTypeID).findAll();
        List<DeviceTypeStatusCodes> copied = realm.copyFromRealm(results);
        return copied;
    }

    public List<AllCustomers> getAllCustomerList() {
        return realm.where(AllCustomers.class).findAll();
    }


    public FireBugEquipment getEquipment(String barcodeID) {
        return realm.where(FireBugEquipment.class).equalTo("Code", barcodeID).findFirst();
    }


    public SyncCustomer getSyncGetResponseDTO(int ID) {
        return realm.where(SyncCustomer.class).equalTo("CustomerId", ID).findFirst();
    }

    public RealmSyncGetResponseDTO getSyncGetResponse() {
        return realm.where(RealmSyncGetResponseDTO.class).findFirst();
    }

    public boolean isServiceCompany(){

        AllCustomers allCustomers = realm.where(AllCustomers.class).equalTo("IsServiceCompany", true).findFirst();
        if (null!=allCustomers) {
            return true;
        } else {
            return false;
        }
    }


    public AllCustomers getParentCompany(){

        return realm.where(AllCustomers.class).equalTo("IsServiceCompany", true).findFirst();

    }

    public RealmSyncGetResponseDTO getSyncRealmGetResponseDTO() {
        return realm.where(RealmSyncGetResponseDTO.class).findFirst();
    }


}

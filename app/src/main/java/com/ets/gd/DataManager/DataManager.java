package com.ets.gd.DataManager;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.InspectionDates;
import com.ets.gd.Models.Location;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteInspection;
import com.ets.gd.NetworkLayer.ResponseDTOs.Routes;
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
            myLocation.setSite(locations.getSite().getID());
            myLocation.setBuilding(locations.getBuilding().getID());
        }

        if (operation.startsWith("m")) {
            Locations locations = realm.where(Locations.class).equalTo("ID", Integer.parseInt(newLocId)).findFirst();
            myLocation.setCustomer(locations.getCustomer().getID());
        } else {
            myLocation.setCustomer(cusID);
        }

        for (FireBugEquipment asset : assetList) {
            FireBugEquipment assett = realm.where(FireBugEquipment.class).equalTo("ID", asset.getID()).findFirst();
            assett.setLocation(myLocation);
            if (operation.startsWith("m")) {
                assett.setMoved(true);
            } else {
                assett.setTransferred(true);
                assett.setCustomer(DataManager.getInstance().getCustomerByID(cusID));
            }

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

    public void addUpdateAssetNote(final int equipmentID, final String  equipmentCode, final int customerID, final List<EquipmentNote> noteList) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SyncCustomer realmSyncGetResponse = realm.where(SyncCustomer.class).equalTo("CustomerId", customerID).findFirst();
                //RealmResults<EquipmentNote> oldList = realm.where(EquipmentNote.class).equalTo("EquipmentID",equipmentID).findAll();
                // RealmList<EquipmentNote> res = new RealmList<EquipmentNote>();
                // res.addAll(oldList);
                //RealmList<EquipmentNote> newItems = new RealmList<EquipmentNote>();

                for (int i = 0; i < noteList.size(); i++) {
                    EquipmentNote equipmentNote = realm.createObject(EquipmentNote.class);
                    equipmentNote.setNote(noteList.get(i).getNote());
                    equipmentNote.setEquipmentID(equipmentID);
                    equipmentNote.setEquipmentCode(equipmentCode);
                    equipmentNote.setModifiedTime(noteList.get(i).getModifiedTime());
                    equipmentNote.setModifiedBy(noteList.get(i).getModifiedBy());
                    // newItems.add(equipmentNote);
                }

                if (0 != noteList.size()) {
                    FireBugEquipment fireBugEquipment = realm.where(FireBugEquipment.class).equalTo("ID", equipmentID).findFirst();
                    //res.addAll(newItems);
                    fireBugEquipment.setUpdated(true);
                    // realmSyncGetResponse.setLstFbEquipmentNotes(res);
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

    public Locations getLocationByID(int ID) {
        return realm.where(Locations.class).equalTo("ID", ID).findFirst();
    }

    public List<Locations> getCustomerLocations(int ID) {
        return realm.where(SyncCustomer.class).equalTo("CustomerId", ID).findFirst().getLstLocations();
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


    public List<Site> getAllSites() {
        return realm.where(Site.class).findAll();
    }

    public List<Building> getAllBuildings() {
        return realm.where(Building.class).findAll();
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
        return realm.where(Model.class).equalTo("Manufacturer", id).findAll();
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

    public List<EquipmentNote> getAllNotesByCode(String EquipmentCode) {

        RealmResults<EquipmentNote> results = realm.where(EquipmentNote.class).equalTo("EquipmentCode", EquipmentCode).findAll();
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
    public List<Locations> getAllCompanyLocations(int compCode) {
        return realm.where(Locations.class).equalTo("Customer.ID", compCode).findAll();
    }

    // For getting asset all locations from DB
    public List<FireBugEquipment> getAllCompanyAssets(int compCode) {
        return realm.where(FireBugEquipment.class).equalTo("Customer.ID", compCode).findAll();
    }

    // For getting asset all locations from DB
    public List<Locations> getAllRepairLocations() {
        return realm.where(Locations.class).equalTo("locationTypeVM.Code", "Repairs").findAll();
    }

    // For getting asset all locations from DB
    public List<Locations> getAllSpareLocations() {
        return realm.where(Locations.class).equalTo("locationTypeVM.Code", "Spares").findAll();
    }


    // For getting asset all assets from DB
    public Customer getCustomerByID(int Code) {
        if (null != realm.where(Customer.class).equalTo("ID", Code).findFirst()) {
            return realm.where(Customer.class).equalTo("ID", Code).findFirst();
        } else {
            realm.beginTransaction();
            AllCustomers allCustomer = realm.where(AllCustomers.class).equalTo("ID", Code).findFirst();
            Customer customer = realm.createObject(Customer.class, allCustomer.getID());
            customer.setDescription(allCustomer.getDescription());
            customer.setCode(allCustomer.getCode());
            customer.setApplicationID(allCustomer.getApplicationID());
            customer.setServiceCompany(allCustomer.isServiceCompany());
            realm.commitTransaction();
            return customer;
        }

    }


    // For getting asset all locations from DB
    public void addAllLocationstoMyLocations(List<Locations> lstLocations) {
        RealmResults<Locations> results = realm.where(Locations.class).findAllSorted("ID");

    }




    // For getting asset all assets from DB
    public List<Routes> getAllInspectionRoutes() {
        RealmResults<Routes> results = realm.where(Routes.class).findAll();

        List<Routes> copied = realm.copyFromRealm(results);

        return copied;
    }

    public void saveSyncGetResponse(final SyncGetResponseDTO obj) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
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
                realmSyncGetResponseDTO.setLstFbEquipmentNotes(obj.getLstFbEquipmentNotes());
                realmSyncGetResponseDTO.setLstRoutes(obj.getLstRoutes());
                realm.copyToRealmOrUpdate(realmSyncGetResponseDTO);
            }
        });
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
                unitinspectionResult.setNewLocationID(inspectionResult.getNewLocationID());
                unitinspectionResult.setNewEquipmentID(inspectionResult.getNewEquipmentID());
                unitinspectionResult.setReplaceType(inspectionResult.getReplaceType());
                unitinspectionResult.setReplaced(inspectionResult.getReplaced());

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



    public List<RouteInspection> getAllRouteInspectionTypes(int RouteID) {
        return realm.where(RouteInspection.class).equalTo("RouteID",RouteID).findAll();
    }



    public FireBugEquipment getEquipment(String barcodeID) {
        return realm.where(FireBugEquipment.class).equalTo("Code", barcodeID).findFirst();
    }

    public FireBugEquipment getEquipmentByID(int ID) {
        return realm.where(FireBugEquipment.class).equalTo("ID", ID).findFirst();
    }

    public FireBugEquipment getEquipmentForCompany(String compCode, String assetCode) {
        List<FireBugEquipment> list = realm.where(SyncCustomer.class).equalTo("CustomerId", getCustomerByCode(compCode).getID()).findFirst().getLstFbEquipments();

        for (FireBugEquipment equip : list) {
            if (equip.getCode() == assetCode)
                return equip;
        }
        return null;
    }

    public List<FireBugEquipment> getCompanyEquipments(int ID) {
        return realm.where(FireBugEquipment.class).equalTo("Customer.ID", ID).findAll();
    }

    public SyncCustomer getSyncGetResponseDTO(int ID) {
        return realm.where(SyncCustomer.class).equalTo("CustomerId", ID).findFirst();
    }

    public RealmSyncGetResponseDTO getSyncGetResponse() {
        return realm.where(RealmSyncGetResponseDTO.class).findFirst();
    }

    public boolean isServiceCompany() {

        AllCustomers allCustomers = realm.where(AllCustomers.class).equalTo("IsServiceCompany", true).findFirst();
        if (null != allCustomers) {
            return true;
        } else {
            return false;
        }
    }


    public AllCustomers getParentCompany() {

        return realm.where(AllCustomers.class).equalTo("IsServiceCompany", true).findFirst();

    }

    public RealmSyncGetResponseDTO getSyncRealmGetResponseDTO() {
        return realm.where(RealmSyncGetResponseDTO.class).findFirst();
    }


}

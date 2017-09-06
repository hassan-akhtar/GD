package com.ets.gd.NetworkLayer.ResponseDTOs;


import io.realm.RealmList;


public class SyncGetResponseDTO extends ResponseDTO {

    private String Version;
    RealmList<AllCustomers> lstCustomers;
    RealmList<SyncCustomer> lstCustomerData;
    RealmList<DeviceType> lstDeviceType;
    RealmList<Manufacturer> lstManufacturers;
    RealmList<Model> lstModels;
    RealmList<VendorCode> lstVendorCodes;
    RealmList<AgentType> lstAgentTypes;
    RealmList<RegisteredDevice> lstDevices;
    RealmList<EquipmentNote> lstFbEquipmentNotes;
    RealmList<JobNumber> lstJobNumber;
    RealmList<Category> lstCategory;
    RealmList<Routes> lstRoutes;
    DashboardStats  mobileDashboard;
    RealmList<FirebugBuilding> lstFireBugBuilding;
    RealmList<Action> lstMaintenanceAction;
    RealmList<MaintenanceCategory> lstMaintenanceCategory;
    RealmList<Material> lstMaterials;
    RealmList<SortOrderInventory>  InventoryMenuSortOrder;
    RealmList<SortOrderFireBug>  FireBugMenuSortOrder;
    RealmList<SortOrderToolHawk>  ToolHawkMenuSortOrder;
    RealmList<FirebugEqSize>  lstSizes;
    RealmList<Condition> lstTHConditions;
     RealmList<Rating> lstRatings;
    private int DueDays;

    public RealmList<EquipmentNote> getLstFbEquipmentNotes() {
        return lstFbEquipmentNotes;
    }

    public void setLstFbEquipmentNotes(RealmList<EquipmentNote> lstFbEquipmentNotes) {
        this.lstFbEquipmentNotes = lstFbEquipmentNotes;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public RealmList<Condition> getLstTHConditions() {
        return lstTHConditions;
    }

    public void setLstTHConditions(RealmList<Condition> lstTHConditions) {
        this.lstTHConditions = lstTHConditions;
    }

    public RealmList<Rating> getLstRatings() {
        return lstRatings;
    }

    public void setLstRatings(RealmList<Rating> lstRatings) {
        this.lstRatings = lstRatings;
    }

    public int getDueDays() {
        return DueDays;
    }

    public void setDueDays(int dueDays) {
        DueDays = dueDays;
    }

    public RealmList<FirebugEqSize> getLstSizes() {
        return lstSizes;
    }

    public void setLstSizes(RealmList<FirebugEqSize> lstSizes) {
        this.lstSizes = lstSizes;
    }

    public RealmList<DeviceType> getLstDeviceType() {
        return lstDeviceType;
    }

    public void setLstDeviceType(RealmList<DeviceType> lstDeviceType) {
        this.lstDeviceType = lstDeviceType;
    }

    public RealmList<MaintenanceCategory> getLstMaintenanceCategory() {
        return lstMaintenanceCategory;
    }

    public RealmList<Material> getLstMaterials() {
        return lstMaterials;
    }

    public void setLstMaterials(RealmList<Material> lstMaterials) {
        this.lstMaterials = lstMaterials;
    }

    public void setLstMaintenanceCategory(RealmList<MaintenanceCategory> lstMaintenanceCategory) {
        this.lstMaintenanceCategory = lstMaintenanceCategory;
    }

    public RealmList<Action> getLstMaintenanceAction() {
        return lstMaintenanceAction;
    }

    public void setLstMaintenanceAction(RealmList<Action> lstMaintenanceAction) {
        this.lstMaintenanceAction = lstMaintenanceAction;
    }

    public RealmList<FirebugBuilding> getLstFireBugBuilding() {
        return lstFireBugBuilding;
    }

    public void setLstFireBugBuilding(RealmList<FirebugBuilding> lstFireBugBuilding) {
        this.lstFireBugBuilding = lstFireBugBuilding;
    }

    public RealmList<SortOrderInventory> getInventoryMenuSortOrder() {
        return InventoryMenuSortOrder;
    }

    public void setInventoryMenuSortOrder(RealmList<SortOrderInventory> inventoryMenuSortOrder) {
        InventoryMenuSortOrder = inventoryMenuSortOrder;
    }

    public RealmList<SortOrderFireBug> getFireBugMenuSortOrder() {
        return FireBugMenuSortOrder;
    }

    public void setFireBugMenuSortOrder(RealmList<SortOrderFireBug> fireBugMenuSortOrder) {
        FireBugMenuSortOrder = fireBugMenuSortOrder;
    }

    public RealmList<SortOrderToolHawk> getToolHawkMenuSortOrder() {
        return ToolHawkMenuSortOrder;
    }

    public void setToolHawkMenuSortOrder(RealmList<SortOrderToolHawk> toolHawkMenuSortOrder) {
        ToolHawkMenuSortOrder = toolHawkMenuSortOrder;
    }

    public RealmList<JobNumber> getLstJobNumber() {
        return lstJobNumber;
    }

    public void setLstJobNumber(RealmList<JobNumber> lstJobNumber) {
        this.lstJobNumber = lstJobNumber;
    }

    public RealmList<Category> getLstCategory() {
        return lstCategory;
    }

    public void setLstCategory(RealmList<Category> lstCategory) {
        this.lstCategory = lstCategory;
    }

    public DashboardStats getMobileDashboard() {
        return mobileDashboard;
    }

    public void setMobileDashboard(DashboardStats mobileDashboard) {
        this.mobileDashboard = mobileDashboard;
    }

    public RealmList<Routes> getLstRoutes() {
        return lstRoutes;
    }

    public void setLstRoutes(RealmList<Routes> lstRoutes) {
        this.lstRoutes = lstRoutes;
    }

    public RealmList<Manufacturer> getLstManufacturers() {
        return lstManufacturers;
    }

    public void setLstManufacturers(RealmList<Manufacturer> lstManufacturers) {
        this.lstManufacturers = lstManufacturers;
    }

    public RealmList<Model> getLstModels() {
        return lstModels;
    }

    public void setLstModels(RealmList<Model> lstModels) {
        this.lstModels = lstModels;
    }

    public RealmList<VendorCode> getLstVendorCodes() {
        return lstVendorCodes;
    }

    public void setLstVendorCodes(RealmList<VendorCode> lstVendorCodes) {
        this.lstVendorCodes = lstVendorCodes;
    }

    public RealmList<AgentType> getLstAgentTypes() {
        return lstAgentTypes;
    }

    public void setLstAgentTypes(RealmList<AgentType> lstAgentTypes) {
        this.lstAgentTypes = lstAgentTypes;
    }

    public RealmList<AllCustomers> getLstCustomers() {
        return lstCustomers;
    }

    public void setLstCustomers(RealmList<AllCustomers> lstCustomers) {
        this.lstCustomers = lstCustomers;
    }

    public RealmList<SyncCustomer> getLstCustomerData() {
        return lstCustomerData;
    }

    public void setLstCustomerData(RealmList<SyncCustomer> lstCustomerData) {
        this.lstCustomerData = lstCustomerData;
    }

    public RealmList<RegisteredDevice> getLstDevices() {
        return lstDevices;
    }

    public void setLstDevices(RealmList<RegisteredDevice> lstDevices) {
        this.lstDevices = lstDevices;
    }


}

package com.ets.gd.NetworkLayer.Service;


import com.ets.gd.NetworkLayer.RequestDTOs.LoginDTO;

public interface GSDService {

     void loginRequest(LoginDTO loginFteDTO, MyCallBack callback);

}

package org.jumutang.zsh.services;

import org.jumutang.zsh.model.ReceiveAddress;

import java.util.List;

/**
 * Created by RuanYJ on 2017/3/6.
 */
public interface ReceiveAddressServiceI {


    List<ReceiveAddress> queryReceiveAddressListByUserId(ReceiveAddress receiveAddress);

    int addReceiveAddressModel(ReceiveAddress receiveAddress);

    int updateReceiveAddressModel(ReceiveAddress receiveAddress);
}

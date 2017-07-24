package org.jumutang.zsh.services.impl;

import org.jumutang.zsh.dao.ReceiveAddressDaoI;
import org.jumutang.zsh.model.ReceiveAddress;
import org.jumutang.zsh.services.ReceiveAddressServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RuanYJ on 2017/3/7.
 */
@Service("receiveAddressServiceI")
public class ReceiveAddressServiceImpl implements ReceiveAddressServiceI{

    @Autowired
    private ReceiveAddressDaoI receiveAddressDaoI;

    @Override
    public List<ReceiveAddress> queryReceiveAddressListByUserId(ReceiveAddress receiveAddress) {
        return receiveAddressDaoI.queryReceiveAddressListByUserId(receiveAddress);
    }

    @Override
    public int addReceiveAddressModel(ReceiveAddress receiveAddress) {
        return receiveAddressDaoI.addReceiveAddressModel(receiveAddress);
    }

    @Override
    public int updateReceiveAddressModel(ReceiveAddress receiveAddress) {
        return receiveAddressDaoI.updateReceiveAddressModel(receiveAddress);
    }
}

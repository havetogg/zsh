package org.jumutang.zsh.dao;

import org.jumutang.zsh.model.ReceiveAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by RuanYJ on 2017/3/6.
 */
@Repository
public interface ReceiveAddressDaoI {

    List<ReceiveAddress> queryReceiveAddressListByUserId(ReceiveAddress receiveAddress);

    int addReceiveAddressModel(ReceiveAddress receiveAddress);

    int updateReceiveAddressModel(ReceiveAddress receiveAddress);

    List<ReceiveAddress> queryById(ReceiveAddress receiveAddress);
}

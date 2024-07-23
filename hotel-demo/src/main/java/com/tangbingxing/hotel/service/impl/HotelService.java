package com.tangbingxing.hotel.service.impl;

import com.tangbingxing.hotel.mapper.HotelMapper;
import com.tangbingxing.hotel.pojo.Hotel;
import com.tangbingxing.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {
}

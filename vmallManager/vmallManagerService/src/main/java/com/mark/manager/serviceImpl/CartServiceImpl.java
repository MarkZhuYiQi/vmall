package com.mark.manager.serviceImpl;

import com.mark.common.constant.CartConstant;
import com.mark.common.exception.CartException;
import com.mark.manager.dao.CartDao;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.dto.Courses;
import com.mark.manager.pojo.VproAuth;
import com.mark.manager.pojo.VproCartDetail;
import com.mark.manager.service.CartService;
import com.mark.manager.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Value("${cookieCartPrefix}")
    String cookieCartPrefix;
    @Value("${userCartPrefix}")
    String userCartPrefix;
    @Autowired
    @Qualifier("cartDao")
    CartDao cartDao;

    @Autowired
    CourseService courseService;
    /**
     * 取得id下的购物车信息
     * 如果已经登录了，那么会获得user与购物车id对应的id，没有登录会得到cookieid，如果啥都没有就返回空
     * 登陆状态：
     * 从登陆信息获得用户id，根据用户id找到购物车id，这里开始分情况：
     *     拿到购物车id，就去从缓存和数据库中获得购物车信息
     *     没有拿到购物车id，就去拿一个新的非重复购物车id，传给数据操作层进行创建购物车
     * 最后返回购物车详细信息
     * 未登录状态：
     *
     * @return
     */
    @Override
    public Cart loadUserCartWithLogin(String token) throws CartException {
        Cart cart = new Cart();
        String err = "user cart could not be load! %s";
        try {
            // 尝试获得现有购物车
            VproAuth auth = cartDao.getLoginInfo(token);
            String cartId = cartDao.getCartIdByUserId(auth.getAuthId());
            // 从缓存和数据库获得购物车
            return cartDao.loadUserCart(cartId, String.valueOf(auth.getAuthId()));
        } catch (CartException e) {
            // 没有拿到用户购物车ID，获得一个新的非重复购物车id
            String cartId = cartDao.getNewCartId();
            try {
                // 都没有，就创建购物车
                cartDao.createUserCart(cartId, token);
                cart.setCartId(Long.parseLong(cartId));
                return cart;
            } catch (CartException e1) {
                logger.error(String.format(err, e1.getMsg()));
                throw new CartException(e1.getMsg(), e1.getCode());
            }
        }
    }
    @Override
    public Cart loadUserCartWithoutLogin() {
        return null;
    }

    @Override
    public Boolean addItemToCart(CartDetail cartDetail, String token) throws CartException {
        try {
            // 获得登陆信息
            VproAuth auth = cartDao.getLoginInfo(token);
            // 获得购物车id
            String cartId = cartDao.getCartIdByUserId(auth.getAuthId());
            // 检查购物车是否存在
            if (!cartDao.checkCartExists(cartId)) {
                cartDao.createUserCart(cartId, auth);
            }
            // 检查课程是否存在
            Courses course = courseService.getCourse(cartDetail.getCartCourseId().intValue());
            if (course == null) throw new CartException("course not exist", CartConstant.COURSE_NOT_EXIST);
            // 添加商品
            VproCartDetail cartDetail1 = cartDao.addItem(cartDetail);
            return true;
        } catch (CartException c) {
            logger.warn(c.getMsg());
            throw new CartException(c.getMsg(), c.getCode());
        }
    }

    @Override
    public Boolean addItemToCookieCart(CartDetail cartDetail) throws CartException {
        try {
            Courses course = courseService.getCourse(cartDetail.getCartCourseId().intValue());
            if (course == null) throw new CartException("course not exist", CartConstant.COURSE_NOT_EXIST);
            VproCartDetail cartDetail1 = cartDao.addItem(cartDetail);
            return true;
        } catch (CartException c) {
            logger.warn(c.getMsg());
            throw new CartException(c.getMsg(), c.getCode());
        }
    }

    @Override
    public Boolean delItemFromCart(CartDetail cartDetail) throws CartException {
        try {
            return cartDao.delCartItem(cartDetail);
        } catch (CartException e) {
            logger.warn(e.getMsg());
            throw new CartException(e.getMsg(), e.getCode());
        }
    }

    @Override
    public Boolean mergeCart(String cookieCartId, String token) throws CartException {
        try {
            VproAuth auth = cartDao.getLoginInfo(token);
            String cartId = cartDao.getCartIdByUserId(auth.getAuthId());
            return cartDao.mergeCart(cookieCartId, cartId);
        } catch (CartException e) {
            throw new CartException(e.getMsg(), e.getCode());
        }
    }
}

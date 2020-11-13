package com.toocms.tab.network.parser;

import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;
import com.toocms.tab.network.exception.LogicException;
import com.toocms.tab.network.modle.TooCMSResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.parse.AbstractParser;
import rxhttp.wrapper.utils.Converter;
import rxhttp.wrapper.utils.GsonUtil;

/**
 * Author：Zero
 * Date：2020/9/11 16:15
 */
@Parser(name = "TooCMSResponse")
public class TooCMSParser<T> extends AbstractParser<T> {

    protected TooCMSParser() {
        super();
    }

    public TooCMSParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(@NotNull Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(TooCMSResponse.class, String.class);
        TooCMSResponse<String> tooCMSResponse = Converter.convert(response, type);

        if (TextUtils.equals(tooCMSResponse.getFlag(), "error"))
            throw new LogicException(tooCMSResponse.getFlag(), tooCMSResponse.getMessage(), response);

        T t = GsonUtil.getObject(tooCMSResponse.getData(), mType);

        if (t == null || (mType == String.class && (StringUtils.equals(t.toString(), "{}") || StringUtils.equals(t.toString(), "[]")))) {
            t = (T) tooCMSResponse.getMessage();
        }
        return t;
    }
}

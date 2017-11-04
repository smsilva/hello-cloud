package org.example.hellocloud.persons.control;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class RepositoryInterceptor {

    private static final Logger LOG = Logger.getLogger(RepositoryInterceptor.class.getName());
    
    @AroundInvoke
    public Object interceptor(InvocationContext ctx) throws Exception {
	try {
//	    LOG.log(Level.INFO, "before: {0}", ctx.getMethod());
	    
	    Object proceed = ctx.proceed();
	    
//	    LOG.log(Level.INFO, "after: {0}", ctx.getMethod());
	    
	    return proceed;
	} catch (Exception e) {
	    LOG.warning("Error calling ctx.proceed in modifyGreeting()");
	    return null;
	}
    }

}

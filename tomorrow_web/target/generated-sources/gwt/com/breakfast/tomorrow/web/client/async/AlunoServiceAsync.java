package com.breakfast.tomorrow.web.client.async;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface AlunoServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.breakfast.tomorrow.web.client.async.AlunoService
     */
    void persistir( com.breakfast.tomorrow.web.client.vo.AlunoVO aluno, AsyncCallback<com.breakfast.tomorrow.web.client.vo.AlunoVO> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.breakfast.tomorrow.web.client.async.AlunoService
     */
    void lista( AsyncCallback<java.util.List<com.breakfast.tomorrow.web.client.vo.AlunoVO>> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.breakfast.tomorrow.web.client.async.AlunoService
     */
    void excluir( java.util.Set<com.breakfast.tomorrow.web.client.vo.AlunoVO> aluno, AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static AlunoServiceAsync instance;

        public static final AlunoServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (AlunoServiceAsync) GWT.create( AlunoService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "alunoService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}

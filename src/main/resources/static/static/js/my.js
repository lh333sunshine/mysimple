
function getApiHost(){
	var huaao_host_list_now = getCookie("huaao_host_list_now");
	if(!huaao_host_list_now){
		setCookie ("huaao_host_list_now", "http://"+window.location.host+"/");
	} 
	return getCookie("huaao_host_list_now");
}
function getApiHostList(){
	var huaao_host_list = getCookie("huaao_host_list");
	if(!huaao_host_list){
		setCookie ("huaao_host_list", "http://"+window.location.host+"/,");
	}
	strs = getCookie("huaao_host_list").split(",");
	return strs;
}
function setCookie(name,value) 
{ 
    var Days = 300; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

//读取cookies 
function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]); 
    else 
        return null; 
} 

function sendApi(callback, api, params, page, pageSize){
	if(params == null){
		params = {};
	}
	if(page != null){
		params.page = page;
	}
	if(pageSize != null){
		params.pageSize = pageSize;
	}
	if(api.indexOf("/") > 1){
		api = "/"+api;
	}
 
	if(params.method == null){
		params.method = "POST";
	}
	
	url_now = getApiHost()+api ;
	  
	jQuery.ajax ({ 
		url: url_now, 
		type:params.method, 
		data: JSON.stringify(params), 
		headers: params.headers,
		contentType:"application/json; charset=UTF-8",
		dataType: "json", 
		success: function(result){  
			callback(result);
		},
        error: function (result)
        {
		 	callback(result.responseText);
        } 
	});
 
}
 

function initApiUrl(api, params, page, pageSize){
	if(params == null){
		params = {};
	}
	if(page != null){
		params.page = page;
	}
	if(pageSize != null){
		params.pageSize = pageSize;
	}
	if(api.indexOf("/") > 1){
		api = "/"+api;
	}
	 
	return getApiHost()+api+".do?"+parseParam(params);
}

var parseParam = function(param, key) {
    var paramStr = "";
    if (param instanceof String || param instanceof Number || param instanceof Boolean) {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    } else {
        $.each(param, function(i) {
            var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
            paramStr += '&' + parseParam(this, k);
        });
    }
    return paramStr.substr(1);
};
 
function countTotalPages(records,pageSize){
 
	// "records":328,"page":1,"pageSize":10
	var i = records/pageSize  ;
	if(records %  pageSize != 0){
		i ++;
	}
	return Math.floor(i);
}

function computerJqgridPage(grid_selector ,pageBtn){
	var page = $(grid_selector).getGridParam('page');
	if(pageBtn == 'user'){
		page = $(".ui-pg-input").val();
	}
	if(pageBtn == 'first_grid-pager'){
		page = 1;
	}
	if(pageBtn == 'prev_grid-pager'){
		page = page - 1;
	}
	if(pageBtn == 'next_grid-pager'){
		page = page + 1;
	}
	if(pageBtn == 'user'){
		page = $(".ui-pg-input").val();
		
	}
	
	if(page < 1){
		page = 1;
	}
	if(page > total_pages){
		page = total_pages;
	}
	return page;
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
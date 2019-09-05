// ====== 配置 === //
const config = {
    host: "http://localhost:8080/apimock_war_exploded/"
};
// ============== //

var vm = new Vue({
    el: "#app",
    data: {
        // 注册API的时候需要的数据
        name: "",
        description: "",
        path: "",
        method: "GET",
        response: "",
        // 服务器地址
        serverPath: config.host,
        // 保存所有API的列表
        apiList: [
            //     {
            //     "id": "UUTFFFOO00099334",
            //     "name": "增加商品",
            //     "description": "这个API用来增加商品",
            //     "path": "http://localhost:8080/apimock_war_exploded/api/goto.do",
            //     "method": "GET",
            //     "response": "{'age':19,'name':'张三'}"
            //     "date":"" 
            // }
        ],
        // 搜索框内容
        keyword: "",
    },
    methods: {
        // 注册API
        registerApi() {
            let param = new URLSearchParams();
            param.append("name", this.name);
            param.append("description", this.description);
            param.append("path", this.path + ".do");
            param.append("requestMethod", this.method);
            param.append("responseBody", this.response);
            axios.post(this.serverPath + "register_api_re", param).then(res => {
                let data = res.data;
                if (data.status != 0) {
                    alert("添加API失败：" + data.msg);
                    return;
                }
                alert("添加成功");
                this.reSetData();
                this.showAllApis();
            }).catch(err => {
                alert(err);
            });
        },
        // 列出所有API
        showAllApis() {
            axios.get(this.serverPath + "get_all_api").then(res => {
                let data = res.data;
                if (data.status != 0) {
                    alert("获取所有API失败：" + data.msg);
                    return;
                }
                this.apiList = data.responseBody;
            }).catch(err => {
                alert(err);
            });
        },
        // 从API名称和说明中搜索有指定关键字的API,构成一个数组返回
        lookupApi(keyword) {
            keyword = keyword.trim();
            let list = [];
            for (let i = 0; i < this.apiList.length; i++) {
                if (this.apiList[i].name.indexOf(keyword) != -1 || this.apiList[i].description.indexOf(keyword) != -1) {
                    list.push(this.apiList[i]);
                }
            }
            return list;
        },
        // 编辑指定的API
        edit(item) {
            console.log(item);

            this.name = item.name;
            this.description = item.description;
            let index = item.path.indexOf(this.serverPath);
            this.path = item.path.substring(index + this.serverPath.length);
            this.method = item.method;
            this.response = item.response;
        },
        // 删除一个API条目
        deleteItem(id) {
            let param = new URLSearchParams();
            param.append("id", id);
            axios.post(this.serverPath + "un_register_api_by_id", param).then(res => {
                let data = res.data;
                if (data.status != 0) {
                    alert("删除失败：" + data.msg);
                    return;
                }
                this.showAllApis();
            }).catch(err => {
                alert(err);
            });
        },
        // 删除全部API
        deleteAll() {
            let code = confirm("确定删除全部?");
            if (!code) return;
            axios.post(this.serverPath + "un_register_all_api").then(res => {
                let data = res.data;
                if (data.status != 0) {
                    alert("删除全部失败：" + data.msg);
                    return;
                }
                this.showAllApis();
            }).catch(err => {
                alert(err);
            });
        },
        // 重置添加API需要的数据
        reSetData() {
            this.name = "";
            this.path = "";
            this.response = "";
            this.methods = "";
            this.description = "";
            this.method = "GET";
        }
    },
    created() {
        // this.showAllApis();
    }
})
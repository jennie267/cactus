<template>
        <div class="col">
        <modal :show.sync="modals.modal1">
          <div>
          <div style="font-weight:bold"><span class="glyphicon glyphicon-align-justify"></span>내 자녀 추가</div>
          <br>
          <div class="row">
          <div class="search-wrapper panel-heading col-sm-12">
          <input class="form-control" type="text" placeholder="자녀의 아이디름 입력해주세요."
                 @keyup.enter="findUser" v-model="searchId" />
          <br>
          </div>
          </div>
          <div class="table-responsive">
        <base-table class="table align-items-center table-flush" :data="tableData">
          <template slot="columns" style="font-size: 15px;">
<!--              <th style="font-size:14px;">사진</th>-->
            <th style="font-size:14px;">아이디</th>
            <th style="font-size:14px;">이름</th>
              <th style="font-size:14px;">생년월일</th>
            <th></th>
          </template>
          <template slot-scope="{row}">
<!--              <td class="name" style="font-size: 20px; cursor:pointer; width:15%;" @click.prevent="showModal(row.messageId, row.sendUserName)">
                  <a href="#" class="avatar avatar-sm rounded-circle">
                      <img alt="" :src="row.profileUrl" style="width:90%;">
                  </a>
              </td>-->
            <td class="name" style="font-size: 13px; cursor:pointer">
            {{row.id}}
            </td>
            <td class="name" style="font-size: 13px; cursor:pointer">
              {{row.name}}
          </td>
              <td class="name" style="font-size: 13px; cursor:pointer">
                  {{row.birthday}}
              </td>
            <td>
            <input type="button" class="btn btn-sm btn-primary" value="┼" @click="addChild(row.userId)">
            </td>
          </template>
        </base-table>
        </div>
      </div>
      <hr class="my-4"/>
      <base-button type="button" style="float: right;" class="btn btn btn-outline-primary" @click="modals.modal1=false">나가기</base-button>
      </modal>
      </div>
</template>
<script>
import Modal from "@/components/Modal.vue";
let children = [];
export default {
  mounted () {
  },
  components: {
    Modal,
  },
  data() {
    return {
        searchId: null,
      children:children,
      user: this.$store.state.user,
      checked:[],
      modals: {
        modal1:false
      },
              tableData: [
        ]
    }
  }, methods:{
        openModal: function (){
            this.modals.modal1 = true;
        },
        findUser: function () {
            this.$http.get(`/user/like/`+ this.searchId,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                .then(res => {
                    console.log(res.data.users);
                    this.tableData = res.data.users;

                });
        },
        addChild: function (childId) {
            // 자녀등록
            this.$http.post(`/user/child/${this.user.userId}/`+childId, null,
                {
                    headers: {
                        Authorization: `Bearer ${this.user.token}`
                        ,'Content-Type':'application/json'
                    }
                })
                .then(res => {
                    if(res.status===200){
                        this.$swal({
                            type: 'success',
                            title: '자녀를 등록했습니다.'
                        });
                        this.modals.modal1 = false;

                        this.$http.post('/user/parent/'+childId+'/'+this.user.userId, null,
                            {
                                headers: {
                                    Authorization: `Bearer ${this.user.token}`
                                    ,'Content-Type':'application/json'
                                }
                            })
                            .then(res => {
                                if(res.status===200) {
                                    this.$parent.addChildren(res.data);
                                }
                            });
                    }
                });
        }
    }
}
</script>
<style>
.headerDivider {
     border-left:0.5px solid #38546d;
     border-right:0.5px solid #16222c;
     height:200px;
     position:absolute;
     right:249px;
     top:10px;
}
</style>

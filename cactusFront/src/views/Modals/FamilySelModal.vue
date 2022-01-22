<template>
        <div class="col">
        <input style="float: right;" @click="modals.modal1=true" class="btn btn btn-outline-primary" value="내 부모 추가">
        <modal :show.sync="modals.modal1">
          <div>
          <div style="font-weight:bold"><span class="glyphicon glyphicon-align-justify"></span>내 부모 추가</div>
          <br>
          <div class="row">
          <div class="search-wrapper panel-heading col-sm-12">
          <input class="form-control" type="text" placeholder="Search" />
          <br>
          </div>                        
          </div>
          <div class="table-responsive">
        <base-table class="table align-items-center table-flush" :data="tableData">
        <template slot="columns" style="font-size: 15px;">
          <th style="font-size:14px;">아이디</th>
          <th style="font-size:14px;">이름</th>
          <th></th>
        </template>
        <template slot-scope="{row}">
          <td class="name" style="font-size: 13px; cursor:pointer">
          {{row.id}}
          </td>
          <td class="name" style="font-size: 13px; cursor:pointer">
          {{row.name}}
          </td>
          <td>
          <input type="button" class="btn btn-sm btn-primary" value="┼">
          </td>
        </template>
      </base-table>
    </div>
      <hr class="my-4"/>
      <base-button type="button" style="float: right;" class="btn btn btn-outline-primary" @click="modals.modal1=false">나가기</base-button>
      </div>
    </modal>
    </div>
</template>
<script>
import Modal from "@/components/Modal.vue";
let parents = [];
export default {
  mounted () {
    this.$http.get(`/user/parents/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
        .then(res => {
            parents = [];
            console.log(res.data);
            res.data.users.forEach(par => parents.push(par));

        });
  },
  components: {
    Modal,
  },
  data() {
    return {
      parents:parents,
      user: this.$store.state.user,
      checked:[],
      modals: {
        modal1:false
      },
              tableData: [
          {
            id: 'eakim',
            name: '김은아',
          },
          {
            id: 'eakim2',
            name: '김은아2',
          },
          {
            id: 'eakim3',
            name: '김은아3',
          }        
        ]
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
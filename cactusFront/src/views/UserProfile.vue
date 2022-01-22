<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8">
            <div class="card-header bg-transparent row align-items-center">
                <h1 class=""><h1 class="ni ni-badge"></h1>  {{user.name}}님의 개인정보  </h1>
            </div>
        </base-header>
        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col-xl-8 order-xl-1">
                    <div class="card card-profile shadow">
                        <div class="row justify-content-center">
                            <div class="col-lg-3 order-lg-2">
                                <div class="card-profile-image">
                                    <a href="#">
                                         <img v-if="user.profileUrl != null" alt="" :src="user.profileUrl" class="rounded-circle">
                                         <img v-if="user.profileUrl == null" src="img/theme/typeUser.jpg" class="rounded-circle">
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="card-header text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4">
                            <div class="d-flex justify-content-between">
                            </div>
                        </div>
                        <div class="card-body pt-0 pt-md-4">
                            <div class="row">
                                <div class="col">
                                    <div class="card-profile-stats d-flex justify-content-center mt-md-5">
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <h2>{{user.name}}</h2>
                                 <a @click="$refs.imageBtn.click()" class="btn btn-sm btn-primary, ni ni-image trigger"> 프로필 사진 수정  </a>

                            </div>
                            <div class="text-center fileBtn">
                                <input type="file" ref="imageBtn"  id="imageBtn" accept="image/*" @change="onFileChange" >
                            </div>
                        </div>
                    </div>
                    <card type="secondary">
                        <div slot="header" class="bg-white border-0">
                            <div class="row align-items-center">
                                <div class="col-8">
                                    <h3 class="mb-0">내 정보</h3>
                                </div>
                            </div>
                        </div>
                        <template>
                            <form @submit.prevent>
                                <div class="pl-lg-4">
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <base-input alternative=""
                                                        label="아이디"
                                                        placeholder="Id"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.id"
                                                        readonly="true"
                                            />
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <base-input alternative=""
                                                        label="비밀번호"
                                                        placeholder="Password"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.password"
                                                        type="password"
                                            />
                                            <small>{{ pwValidation }}</small>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <base-input alternative=""
                                                        label="비밀번호 확인"
                                                        placeholder="Password"
                                                        type="password"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.passwordchk"
                                            />
                                            <!--
                                        <small>{{ pwCheck }}</small>
                                        -->
                                        </div>
                                    </div>
                                    <hr class="my-4"/>
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <base-input alternative=""
                                                        label="이름"
                                                        placeholder="Username"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.name"
                                            />
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <base-input label="성별">
                                                <div class="custom-control custom-radio mb-3">
                                                    <input name="custom-radio-1" class="custom-control-input" id="customRadio5" type="radio" v-model="modUser.gender" value="여">
                                                    <label class="custom-control-label" for="customRadio5"><span>여성</span></label>
                                                </div>
                                                <div class="custom-control custom-radio mb-3">
                                                    <input name="custom-radio-1" class="custom-control-input" id="customRadio6" type="radio" v-model="modUser.gender" value="남">
                                                    <label class="custom-control-label" for="customRadio6"><span>남성</span></label>
                                                </div>
                                            </base-input>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <base-input alternative=""
                                                        label="이메일"
                                                        placeholder="mail@example.com"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.email"
                                            />
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <base-input alternative=""
                                                        label="전화번호"
                                                        placeholder="Phone number"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.tel"
                                            />
                                        </div>
                                    </div>
                                  <div class="row">
                                    <div class="col-lg-8">
                                    <base-input label="생일">
                                      <div addon-left-icon="ni ni-calendar-grid-58">
                                                            </div>
                                        <flat-pickr slot-scope="{focus, blur}"
                                              @on-open="focus"
                                              @on-close="blur"
                                              :config="{allowInput: true}"
                                              class="form-control datepicker"
                                              placeholder="birthday"
                                              v-model="modUser.birthday">
                                        </flat-pickr>
                                    </base-input>
                                    </div>
                                  </div>
                                  <div class="pl-lg-4">
                                    <div
                                        ref="searchWindow"
                                        :style="searchWindow"
                                        style="border:1px solid;width:500px;margin:5px 0;position:relative"
                                        >
                                        <img
                                            src="//t1.daumcdn.net/postcode/resource/images/close.png"
                                            id="btnFoldWrap"
                                            style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1"
                                            @click="searchWindow.display = 'none'"
                                            alt="close"
                                        >
                                    </div>
                                  </div>
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <base-input alternative=""
                                                        label="주소"
                                                        placeholder="Address"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.addrMain"
                                            />
                                        </div>
                                        <div class="col-lg-4">
                                            <base-input alternative=""
                                                        label="우편번호"
                                                        placeholder="zipCode"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.zipCode"
                                            />
                                        </div>
                                        <input type="button" class="btn btn-sm btn-primary" value="우편번호 찾기" @click="execDaumPostcode">
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <base-input alternative=""
                                                        label="상세 주소"
                                                        placeholder="Address"
                                                        input-classes="form-control-alternative"
                                                        v-model="modUser.addrSub"
                                                        ref="addrSub"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4 text-right" style="float:right;">
                                <input @click="doUserUpdate" style="float: right;" type="button" class="btn btn btn-primary" value="저장">
                                </div>
                            </form>
                        </template>

                        <br>
                        <br>
    <hr>

                        <div class="grid1x2">
                            <div class="box_user box3_user">
                                <div>
                                    <parents-table ref="parentT" v-if="user.type === 'CHILD'" @selectUser="selectUser" style="width:100%; height:100%;"></parents-table>
                                    <children-table ref="childTable" v-if="user.type === 'PARENT'" @selectUser="selectUser" style="width:100%; height:100%;"></children-table>
                                </div>
                            </div>
                            <div class="box_user box4_user">
                                <div>
                                    <user-profile-detail :chkUser="selectedUser"  :user="user" style="width:100%; height:100%;"></user-profile-detail>
                                </div>
                            </div>
                        </div>



                    </card>
                </div>
            </div>
        </div>
    </div>
</template>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="/assets/vendor/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>

<script>
import Vue from 'vue'
import Datetime from 'vue-datetime'
import 'vue-datetime/dist/vue-datetime.css'
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import FamilySelModal from './Modals/FamilySelModal';
import ChildSelModal from './Modals/ChildSelModal';
import ParentsTable from './UserProfile/ParentsTable'
import ChildrenTable from './UserProfile/ChildrenTable'
import UserProfileDetail from './UserProfile/UserProfileDetail'
import axios from 'axios'

Vue.use(VueSweetalert2);
Vue.prototype.$http=axios
let modUser = {};
export default {
    name: 'profile',
        props: {
      type: {
        type: String
      },
      date: String
    },
    data() {
      return {
        user: this.$store.state.user
        ,modUser: JSON.parse(JSON.stringify(this.$store.state.user))
        ,checked:[]
          ,selectedUser: {}
        ,searchWindow: {
        display: 'none',
        height: '300px',
        }
      }
	},
    mounted () {
        this.modUser.password = null;
    },
	components: {
      flatPickr,
      FamilySelModal,
      ChildSelModal,
        ParentsTable,
        ChildrenTable,
        UserProfileDetail
    },
    methods: {
        onFileChange(e) {
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            console.log('파일들어옴? ' , files[0]);

            let formData = new FormData();
            formData.append("file", files[0]);


            this.$http.post(`/user/profile/${this.user.userId}`, formData,
                {
                    headers: {
                        Authorization: `Bearer ${this.user.token}`
                        ,'Content-Type':'multipart/form-data'
                    }
                })
                .then(res => {
                    this.$store.state.user.profileUrl = res.data.profileUrl;
                    this.$swal({
                        type: 'success',
                        title: '프로필 사진을 변경했습니다.'
                    });

                });
        },
        selectUser(user) {
            this.selectedUser = user
        },
        delUserId: function(){
            console.log('왜안들어오?');
            if (this.user.type == 'PARENT'){
                this.$refs.childTable.getChildren();
            }else if (this.user.type == 'CHILD'){

            }
        },
    execDaumPostcode() {
      const currentScroll = Math.max(
        document.body.scrollTop,
        document.documentElement.scrollTop,
      );
      new daum.Postcode({
        onComplete: (data) => {
          if (data.userSelectedType === 'R') {
            this.modUser.addrMain = data.roadAddress;
          } else {
            this.modUser.addrMain = data.jibunAddress;
          }
          if (data.userSelectedType === 'R') {
            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
              this.modUser.addrSub += data.bname;
            }

            if (data.buildingName !== '' && data.apartment === 'Y') {
              this.modUser.addrSub +=
                this.modUser.addrSub !== ''
                  ? `, ${data.buildingName}`
                  : data.buildingName;
            }

            if (this.modUser.addrSub !== '') {
              this.modUser.addrSub = ` (${this.modUser.addrSub})`;
            }
          } else {
            this.modUser.addrSub = '';
          }

          this.modUser.zipCode = data.zonecode;

          this.searchWindow.display = 'none';
          document.body.scrollTop = currentScroll;
          this.$router.go(-1);

        },
        onResize: (size) => {
          this.searchWindow.height = `${size.height}px`;
        },
        width: '100%',
        height: '100%',
      }).embed(this.$refs.searchWindow);

      this.searchWindow.display = 'block';
    },
    doUserUpdate() {
        if (this.modUser.password==null||this.modUser.password=="") {
            this.$swal({
                type: 'warning',
                title: '비밀번호를 입력하여 주십시오.'
            });
        } else if(this.modUser.password.length <8){
            this.$swal({
                type: 'warning',
                title: '비밀번호는 8자리 이상이어야 합니다.'
            });
        }else if (this.modUser.passwordchk==null||this.modUser.passwordchk=="") {
            this.$swal({
                type: 'warning',
                title: '비밀번호확인을 입력하여 주십시오.'
            });
        } else if(this.modUser.password != this.modUser.passwordchk){
            this.$swal({
                type: 'warning',
                title: '비밀번호와 비밀번호확인이 같지 않습니다.'
            });
        }else if (this.modUser.gender==null||this.modUser.gender=="") {
            this.$swal({
                type: 'warning',
                title: '성별을 입력하여 주십시오.'
            });
        }else if (this.modUser.email==null||this.modUser.email=="") {
            this.$swal({
                type: 'warning',
                title: '이메일을 입력하여 주십시오.'
            });
        }else if (this.modUser.name==null||this.modUser.name=="") {
            this.$swal({
                type: 'warning',
                title: '이름을 입력하여 주십시오.'
            });
        }else {
            this.$http.post(`/user/update/`, this.modUser,
          {
            headers: {
                Authorization: `Bearer ${this.modUser.token}`
                ,'Content-Type':'application/json'
            },
          })
          .then(res => {
            console.log(this.modUser);
              console.log('전송');
              console.log(res);
              console.log(res.data);
              Vue.swal('회원정보가 수정되었습니다.');
          });
        }
    }
  },
  computed: {
  pwValidation: function() {
      // if(!this.modUser.password.length==0) {
      //  return this.modUser.password.length > 8 ? `` : `[주의] 비밀번호는 8자 이상으로 작성해주세요.`;
      // }
  },
  //pwCheck : function() {
      //  if(!this.modUser.passwordchk.length==0) {
      //   if(this.modUser.password != this.modUser.passwordchk) {
      //    return `[주의] 비밀번호가 동일하지 않습니다.`;
      //   }
      // }
   // }
  }
};
</script>
<style>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

.fileBtn {
    display: none;
}


.box_user {
    margin: 10px;
    display: flex;
    flex-basis: calc(50% - 20px);
    /*justify-content: center;*/
    flex-direction: column;
}
.grid1x2 {
    min-height: 100%;
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
}

.grid1X2 > div {
    /*        display: flex;
            flex-basis: calc(50% - 40px);
            !*justify-content: center;*!
            flex-direction: column;*/
}
.grid1X2 > div > div {
    display: flex;
    justify-content: center;
    flex-direction: row;
}

.box3_user {
    flex-basis: calc(50% - 20px);
}
.box4_user {
    flex-basis: calc(50% - 20px);
}

.box3Header2 {
    background-color: #f3c32a;
}
.box4Header2 {
    background-color: #838bae;
}
</style>

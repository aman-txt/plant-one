import Vue from "vue";
import Vuex from "vuex";
import { default as APIs } from "@/services/apis";

const state = {
  currentUser: {},
};

const getters = {
  getCurrentUser: (state) => state.currentUser,
};

const mutations = {
  SET_CURRENT_USER_DETAILS: (state, userDetails) => {
    state.currentUser = userDetails;
  },
};

const actions = {
  SET_CURRENT_USER_DETAILS: (context, userID) => {
    return APIs.getUser(userID).then(response => {
        context.commit("SET_CURRENT_USER_DETAILS", response.data);
      }).catch((e) => {
        window.console.error(e);
      });
  },
};

Vue.use(Vuex);

// ---- vuex store ----- //
var AppStore = new Vuex.Store({ state, getters, actions, mutations });

export default AppStore;

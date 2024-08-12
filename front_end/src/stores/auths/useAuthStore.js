import { defineStore } from 'pinia';
import useAuthState from './authState.js';
import useAuthGetters from './authGetters.js';
import useAuthActions from './authActions.js';

export const useAuthStore = defineStore('auth', () => {
  const state = useAuthState();
  const getters = useAuthGetters(state);
  const actions = useAuthActions(state);

  return {
    ...state,
    ...getters,
    ...actions,
  };
});

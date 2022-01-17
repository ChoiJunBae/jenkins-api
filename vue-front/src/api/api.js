import axios from 'axios';

//jenkins crumb token 요청하기
async function getToken(){
  const response = await axios.get('');
}

//생성한 axios api export
export {
  getToken
}


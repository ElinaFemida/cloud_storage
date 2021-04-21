package new_chat.server;

    import db.UsersDB;


    public class AuthenticationService {


        public String findNicknameByLoginAndPassword(String nickname, String password) {
            if (UsersDB.doesUserExist(nickname, password)) {
                System.out.println(nickname);
                return nickname;
            }
            return null;
        }

    }

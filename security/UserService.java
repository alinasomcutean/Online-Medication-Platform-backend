//package ds.assign1.DS2020_30444_Somcutean_Alina_Ioana_1.security;
//
//import ds.assign1.DS2020_30444_Somcutean_Alina_Ioana_1.repository.AccountRepo;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    private final AccountRepo accountRepo;
//
//    public UserService(AccountRepo accountRepo) {
//        this.accountRepo = accountRepo;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return accountRepo.findByUsername(username);
//    }
//
//    public String getSessionUserUsername() {
//        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//    }
//}

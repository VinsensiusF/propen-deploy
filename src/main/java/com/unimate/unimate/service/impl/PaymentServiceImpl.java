package com.unimate.unimate.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimate.unimate.dto.SalesPercentageDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Payment;
import com.unimate.unimate.repository.PaymentRepository;
import com.unimate.unimate.service.KelasSiswaService;
import com.unimate.unimate.service.PaymentService;
import java.util.Optional;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.ArrayList;
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KelasSiswaService kelasSiswaService;

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPaymentByStudent(Account siswa) {
        return paymentRepository.findPaymentBySiswaId(siswa.getId());
    }

    @Override
    public Payment getPayment(UUID uuid) {
        
        Optional<Payment> payments = paymentRepository.findById(uuid);

        if (payments.isPresent()) {
            return  payments.get();
        } else {
            return null;
        }
     
    }

    @Override
    public Payment updatePayment(Payment payment, String method, long gross) {
        // TODO Auto-generated method stub
        payment.setMethod(method);
        if (method.equals("bank_transfer")) {
            payment.setNet(Math.toIntExact(gross)-4000);
        }
        if (method.equals("echannel")) {
            payment.setNet(Math.toIntExact(gross)-4000);
        }
        if (method.equals("gopay")) {
            double net = gross * 0.98;
            int netInteger = (int) Math.round(net); 
            payment.setNet(netInteger);
        }
        if (method.equals("qris")) {
            double net = gross * 0.993;
            int netInteger = (int) Math.round(net); 
            payment.setNet(netInteger);
        }
        if (method.equals("shopeepay")) {
            double net = gross * 0.98;
            int netInteger = (int) Math.round(net); 
            payment.setNet(netInteger);
        }
        if (method.equals("credit_card")) {
            double net = gross * 0.971;
            int netInteger = (int) Math.round(net) -2000; 
            payment.setNet(netInteger);
        }

        if (method.equals("credit_card")) {
            double net = gross * 0.971;
            int netInteger = (int) Math.round(net) -2000; 
            payment.setNet(netInteger);
        }

        if (method.equals("cstore")) {
            payment.setNet(Math.toIntExact(gross)-5000);
        }
        Date currentDate = new Date();
        payment.setPayat(currentDate);
        payment.setStatus("Completed");
        paymentRepository.save(payment);
        kelasSiswaService.enrollStudent(payment.getSiswa().getId(), payment.getCourse().getId());
        return payment;
    }

    @Override
    public List<Payment> findPaymentToday() {
        Date curDate = new Date();
  
       return paymentRepository.findAllByDateOrPayat(curDate);
    }

    @Override
    public List<Payment> findPaymentsLast7Days() {
      
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -7);
        Date sevenDaysAgo = calendar.getTime();

        return paymentRepository.findAllByPayatOrDateBetween(sevenDaysAgo, currentDate);
    }

    
    @Override
    public List<Payment> findPaymentsLast30Days() {
      
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -30);
        Date thirtyDaysAgo = calendar.getTime();

        return paymentRepository.findAllByPayatOrDateBetween(thirtyDaysAgo, currentDate);
    }

    @Override
    public SalesPercentageDTO findGPaymentToday() {
        Date curDate = new Date();
      
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        Long todayCount  = paymentRepository.countByDate(curDate);
        Long yesterdayCount  = paymentRepository.countByDate(yesterday);
        todayCount = todayCount != null ? todayCount : 0L;
        yesterdayCount = yesterdayCount != null ? yesterdayCount : 0L;
        
        SalesPercentageDTO salesPercentageDTO = new SalesPercentageDTO();
        double percentage;
        if (todayCount < yesterdayCount) {
            if (yesterdayCount != 0) {
                percentage = ((double)(yesterdayCount - todayCount) / yesterdayCount) * 100;
            } else {
                percentage = 100; 
            }
            salesPercentageDTO.setCentage(percentage);
            salesPercentageDTO.setStatus(false);
        } else if (todayCount > yesterdayCount) {
            if (yesterdayCount != 0) {
                percentage = ((double)(todayCount - yesterdayCount) / yesterdayCount) * 100;
            } else {
                percentage = 100; 
            }
            salesPercentageDTO.setCentage(percentage);
            salesPercentageDTO.setStatus(true);
        }
        
        return salesPercentageDTO;
    }

    @Override
    public SalesPercentageDTO findG2PaymentToday() {
        Date curDate = new Date();
      
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        Long todayCount  = paymentRepository.countByDate2(curDate);
        Long yesterdayCount  = paymentRepository.countByDate2(yesterday);
        todayCount = todayCount != null ? todayCount : 0L;
        yesterdayCount = yesterdayCount != null ? yesterdayCount : 0L;

        SalesPercentageDTO salesPercentageDTO = new SalesPercentageDTO();
        double percentage;
        if (todayCount < yesterdayCount) {
            if (yesterdayCount != 0) {
                percentage = ((double)(yesterdayCount - todayCount) / yesterdayCount) * 100;
            } else {
                percentage = 100; 
            }
            salesPercentageDTO.setCentage(percentage);
            salesPercentageDTO.setStatus(false);
        } else if (todayCount > yesterdayCount) {
            if (yesterdayCount != 0) {
                percentage = ((double)(todayCount - yesterdayCount) / yesterdayCount) * 100;
            } else {
                percentage = 100; 
            }
            salesPercentageDTO.setCentage(percentage);
            salesPercentageDTO.setStatus(true);
        }
        
        
        return salesPercentageDTO;
    }

    @Override
    public SalesPercentageDTO findG2PaymentLast7Days() {
    Date curDate = new Date();
  
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(curDate);
    calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal sekarang
    Date endDate = calendar.getTime();
  
    // mengatur tanggal awal 7 hari yang lalu
    calendar.add(Calendar.DATE, -6); // 6 karena sudah mengurangi satu hari sebelumnya
    Date startDate = calendar.getTime();
  
    Long totalCountEnd = paymentRepository.countByDateRange2(startDate, endDate);
  
    // Menghitung total 7 hari sebelumnya
    calendar.setTime(startDate);
    calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal awal untuk mendapatkan tanggal akhir 7 hari sebelumnya
    endDate = calendar.getTime();
    calendar.add(Calendar.DATE, -6); // mengurangi 6 hari untuk mendapatkan tanggal awal 7 hari sebelumnya
    startDate = calendar.getTime();
  
    Long totalCountStart = paymentRepository.countByDateRange2(startDate, endDate);
  
    // Mengecek jika totalCountStart tidak null, jika null, maka set ke 0L
    totalCountStart = totalCountStart != null ? totalCountStart : 0L;
    totalCountEnd = totalCountEnd != null ? totalCountEnd : 0L;

    SalesPercentageDTO salesPercentageDTO = new SalesPercentageDTO();
    double percentage;
    if (totalCountEnd < totalCountStart) {
        if (totalCountStart != 0) {
            percentage = ((double)(totalCountStart - totalCountEnd) / totalCountStart) * 100;
        } else {
            percentage = 100; // Jika totalCountStart 0, maka dianggap 100% penurunan
        }
        salesPercentageDTO.setCentage(percentage);
        salesPercentageDTO.setStatus(false); // Status false menunjukkan penurunan
    } else if (totalCountEnd > totalCountStart) {
        if (totalCountStart != 0) {
            percentage = ((double)(totalCountEnd - totalCountStart) / totalCountStart) * 100;
        } else {
            percentage = 100; // Jika totalCountStart 0, maka dianggap 100% peningkatan
        }
        salesPercentageDTO.setCentage(percentage);
        salesPercentageDTO.setStatus(true); // Status true menunjukkan peningkatan
    }
  
    return salesPercentageDTO;
}

@Override
public SalesPercentageDTO findGPaymentLast7Days() {
    Date curDate = new Date();
  
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(curDate);
    calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal sekarang
    Date endDate = calendar.getTime();
  
    // mengatur tanggal awal 7 hari yang lalu
    calendar.add(Calendar.DATE, -6); // 6 karena sudah mengurangi satu hari sebelumnya
    Date startDate = calendar.getTime();
  
    Long totalCountEnd = paymentRepository.countByDateRange(startDate, endDate);
  
    // Menghitung total 7 hari sebelumnya
    calendar.setTime(startDate);
    calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal awal untuk mendapatkan tanggal akhir 7 hari sebelumnya
    endDate = calendar.getTime();
    calendar.add(Calendar.DATE, -6); // mengurangi 6 hari untuk mendapatkan tanggal awal 7 hari sebelumnya
    startDate = calendar.getTime();
  
    Long totalCountStart = paymentRepository.countByDateRange(startDate, endDate);
  
    // Mengecek jika totalCountStart tidak null, jika null, maka set ke 0L
    totalCountStart = totalCountStart != null ? totalCountStart : 0L;
    totalCountEnd = totalCountEnd != null ? totalCountEnd : 0L;

    SalesPercentageDTO salesPercentageDTO = new SalesPercentageDTO();
    double percentage;
    if (totalCountEnd < totalCountStart) {
        if (totalCountStart != 0) {
            percentage = ((double)(totalCountStart - totalCountEnd) / totalCountStart) * 100;
        } else {
            percentage = 100; // Jika totalCountStart 0, maka dianggap 100% penurunan
        }
        salesPercentageDTO.setCentage(percentage);
        salesPercentageDTO.setStatus(false); // Status false menunjukkan penurunan
    } else if (totalCountEnd > totalCountStart) {
        if (totalCountStart != 0) {
            percentage = ((double)(totalCountEnd - totalCountStart) / totalCountStart) * 100;
        } else {
            percentage = 100; // Jika totalCountStart 0, maka dianggap 100% peningkatan
        }
        salesPercentageDTO.setCentage(percentage);
        salesPercentageDTO.setStatus(true); // Status true menunjukkan peningkatan
    }
  
    return salesPercentageDTO;
}

    
@Override
public SalesPercentageDTO findG2PaymentLast30Days() {
Date curDate = new Date();

Calendar calendar = Calendar.getInstance();
calendar.setTime(curDate);
calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal sekarang
Date endDate = calendar.getTime();

// mengatur tanggal awal 7 hari yang lalu
calendar.add(Calendar.DATE, -29); // 6 karena sudah mengurangi satu hari sebelumnya
Date startDate = calendar.getTime();

Long totalCountEnd = paymentRepository.countByDateRange2(startDate, endDate);

// Menghitung total 7 hari sebelumnya
calendar.setTime(startDate);
calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal awal untuk mendapatkan tanggal akhir 7 hari sebelumnya
endDate = calendar.getTime();
calendar.add(Calendar.DATE, -29); // mengurangi 6 hari untuk mendapatkan tanggal awal 7 hari sebelumnya
startDate = calendar.getTime();

Long totalCountStart = paymentRepository.countByDateRange2(startDate, endDate);

// Mengecek jika totalCountStart tidak null, jika null, maka set ke 0L
totalCountStart = totalCountStart != null ? totalCountStart : 0L;
totalCountEnd = totalCountEnd != null ? totalCountEnd : 0L;

SalesPercentageDTO salesPercentageDTO = new SalesPercentageDTO();
double percentage;
if (totalCountEnd < totalCountStart) {
    if (totalCountStart != 0) {
        percentage = ((double)(totalCountStart - totalCountEnd) / totalCountStart) * 100;
    } else {
        percentage = 100; // Jika totalCountStart 0, maka dianggap 100% penurunan
    }
    salesPercentageDTO.setCentage(percentage);
    salesPercentageDTO.setStatus(false); // Status false menunjukkan penurunan
} else if (totalCountEnd > totalCountStart) {
    if (totalCountStart != 0) {
        percentage = ((double)(totalCountEnd - totalCountStart) / totalCountStart) * 100;
    } else {
        percentage = 100; // Jika totalCountStart 0, maka dianggap 100% peningkatan
    }
    salesPercentageDTO.setCentage(percentage);
    salesPercentageDTO.setStatus(true); // Status true menunjukkan peningkatan
}

return salesPercentageDTO;
}

@Override
public SalesPercentageDTO findGPaymentLast30Days() {
Date curDate = new Date();

Calendar calendar = Calendar.getInstance();
calendar.setTime(curDate);
calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal sekarang
Date endDate = calendar.getTime();

// mengatur tanggal awal 7 hari yang lalu
calendar.add(Calendar.DATE, -29); // 6 karena sudah mengurangi satu hari sebelumnya
Date startDate = calendar.getTime();

Long totalCountEnd = paymentRepository.countByDateRange(startDate, endDate);

// Menghitung total 7 hari sebelumnya
calendar.setTime(startDate);
calendar.add(Calendar.DATE, -1); // mengurangi satu hari dari tanggal awal untuk mendapatkan tanggal akhir 7 hari sebelumnya
endDate = calendar.getTime();
calendar.add(Calendar.DATE, -29); // mengurangi 6 hari untuk mendapatkan tanggal awal 7 hari sebelumnya
startDate = calendar.getTime();

Long totalCountStart = paymentRepository.countByDateRange(startDate, endDate);

// Mengecek jika totalCountStart tidak null, jika null, maka set ke 0L
totalCountStart = totalCountStart != null ? totalCountStart : 0L;
totalCountEnd = totalCountEnd != null ? totalCountEnd : 0L;

SalesPercentageDTO salesPercentageDTO = new SalesPercentageDTO();
double percentage;
if (totalCountEnd < totalCountStart) {
    if (totalCountStart != 0) {
        percentage = ((double)(totalCountStart - totalCountEnd) / totalCountStart) * 100;
    } else {
        percentage = 100; // Jika totalCountStart 0, maka dianggap 100% penurunan
    }
    salesPercentageDTO.setCentage(percentage);
    salesPercentageDTO.setStatus(false); // Status false menunjukkan penurunan
} else if (totalCountEnd > totalCountStart) {
    if (totalCountStart != 0) {
        percentage = ((double)(totalCountEnd - totalCountStart) / totalCountStart) * 100;
    } else {
        percentage = 100; // Jika totalCountStart 0, maka dianggap 100% peningkatan
    }
    salesPercentageDTO.setCentage(percentage);
    salesPercentageDTO.setStatus(true); // Status true menunjukkan peningkatan
}

return salesPercentageDTO;
}

    public  List<Payment> findBySiswaIdAndCourseId(Long accountId, Long courseId){
        List<String> status = new ArrayList<>();
        status.add("Completed");
        status.add("Pending");
        return paymentRepository.findBySiswaIdAndCourseIdAndStatusIn(accountId, courseId, status);
    }
    
}

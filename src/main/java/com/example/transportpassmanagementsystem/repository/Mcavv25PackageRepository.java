package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.dto.PackageInputRecordsDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Mcavv25PackageRepository extends JpaRepository<Mcavv25Package, Integer> {
    @Query(value = """
                    select A.cavv25_package_name as package_name,A.cavv25_transport_mode as transport,
                    C.cavv25_member_type_name as member_type, A.cavv25_subscription_type as subscription,
                     A.cavv25_validity as validity,A.cavv25_price as price
                    from mcavv25_package A
                    left join mcavv25_member_type_package B ON A.cavv25_package_id=B.cavv25_package_id
                    left join mcavv25_member_type C ON B.cavv25_member_type_id=C.cavv25_member_type_id
                    where :#{#packageInputRecordsDTO.packageName} IS NULL OR :#{#packageInputRecordsDTO.packageName}=''
                    OR A.cavv25_package_name Ilike concat('%',:#{#packageInputRecordsDTO.packageName},'%')
                    ORDER BY A.cavv25_package_id
                    OFFSET :offset ROW FETCH NEXT  :#{#packageInputRecordsDTO.size} ROW only
                    """,nativeQuery = true)
        List<Map<String,Object>> getRecords(@Param("packageInputRecordsDTO") PackageInputRecordsDTO packageInputRecordsDT0,
                                            @Param("offset") int offset);
    @Query(value = """
                select COUNT(*)
                    from mcavv25_package A
                    left join mcavv25_member_type_package B ON A.cavv25_package_id=B.cavv25_package_id
                    left join mcavv25_member_type C ON B.cavv25_member_type_id=C.cavv25_member_type_id
                    where  :#{#packageInputRecordsDTO.packageName} IS NULL OR :#{#packageInputRecordsDTO.packageName}=''
                    OR A.cavv25_package_name Ilike concat('%',:#{#packageInputRecordsDTO.packageName},'%')
                """,nativeQuery = true)
    long count(@Param("packageInputRecordsDTO") PackageInputRecordsDTO packageInputRecordsDTO);
}

package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MCAVV25MemberTypeRepository extends JpaRepository<Mcavv25MemberType, Integer> {

    @Query("""
            select mmt.memberTypeName from Mcavv25MemberType mmt  where mmt.memberTypeName ILIKE concat(%,:memberTye,%)
            """)
    boolean getMemberTypes(String memberTye);

    @Query(value = "SELECT CAVV25_Member_Type_Id AS memberTypeId, " +
            "CAVV25_Member_Type_Name AS memberTypeName " +
            "FROM MCAVV25_Member_Type " +
            "WHERE CAVV25_Member_Type_Name LIKE CONCAT('%', :memberType, '%') " +
            "ORDER BY CAVV25_Member_Type_Id " +
            "OFFSET :offset ROWS " +
            "FETCH NEXT :limit ROWS ONLY",
            nativeQuery = true)
    List<Map<String, Object>> getMemberTypeDetails(String memberType, int offset, int limit);


    @Query(value = "SELECT COUNT(*) FROM MCAVV25_Member_Type " +
            "WHERE CAVV25_Member_Type_Name LIKE CONCAT('%', :memberType, '%')",
            nativeQuery = true)
    long memeberTypeCount(String memberType);

    @Query(value = """
	select * from mcavv25_member_type where cavv25_member_type_name ILike :memberType
	""",nativeQuery = true)
    Optional<Mcavv25MemberType> getMemberType(@Param("memberType") String memberType);

}

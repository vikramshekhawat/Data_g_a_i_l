package com.gail.utility;

public class QueryConstants {
	
	public static final String userExists = "from User where user_name = :userName";
	public static final String payerExists="from Payer where payer_id=:payerId";
	public static final String getContacts="from UserContacts where user_id=:userId";
	public static final String getUserById="from User where user_id=:userId";
	public static final String updateContractText="UPDATE ContractText SET contract_name = :contractName WHERE contract_ref= :contractRef";
	public static final String  getNomination="SELECT n.nomination_id,n.nominated_by,n.contract_id,n.gas_date,n.seller_redel_point,n.seller_updated_date,p.payer_name,u.user_id,p.payer_key,n.revision_no,"
			+ "m.contract_type,c.unit_of_measurements ,m.material_id ,m.material_code,m.material_desc,n.redel_point,n.del_point,n.updated_date,c.start_date ,c.end_date,c.customer_code,c.customer_description,"
			+ "c.contract_ref FROM nominations n INNER JOIN contracts c ON n.contract_id = c.contract_id INNER JOIN material m ON c.material_id = m.material_id  "
			+ "INNER JOIN payer p ON c.payer_id = p.payer_id  INNER JOIN user u ON p.payer_id = u.payer_id WHERE m.contract_type in (contractType) AND c.contract_ref =:contractRef "
			+ "AND u.user_id =:userId AND c.status =:contractStatus AND n.status =:nominationStatus AND n.gas_date =:gasDate And :gasDate between  c.start_date  and c.end_date "
			+ "AND n.nomination_id not in ( select n1.nomination_id from nominations n1, nominations n2  where "
			+ "n1.status = n2.status AND n1.status =:nominationStatus AND  n1.nomination_id < n2.nomination_id  AND n1.contract_id = n2.contract_id AND  n1.gas_date = n2.gas_date) "
			+ "GROUP BY n.`status` , n.contract_id , n.gas_date, n.nominated_by, n.revision_no, n.redel_point,n.del_point, n.updated_date Union SELECT n.nomination_id,"
			+ "n.nominated_by,n.contract_id,n.gas_date,n.seller_redel_point,n.seller_updated_date,p.payer_name, u.user_id,p.payer_key,n.revision_no,m.contract_type,c.unit_of_measurements ,"
			+ "m.material_id ,m.material_code,m.material_desc,n.redel_point,n.del_point,n.updated_date,c.start_date ,c.end_date,c.customer_code,c.customer_description,"
			+ "c.contract_ref FROM nominations n INNER JOIN contracts c ON n.contract_id = c.contract_id INNER JOIN material m ON c.material_id = m.material_id  INNER JOIN payer"
			+ " p ON c.payer_id = p.payer_id  INNER JOIN user u ON p.payer_id = u.payer_id WHERE m.contract_type in (contractType) AND c.contract_ref =:contractRef AND c.status =:contractStatus AND n.status =:nominationStatus AND "
			+ "n.gas_date =:gasDate AND m.material_code in(select material_code from seller where user_id=:userId)";
	public static final String GET_USER_CONTACT_BY_PAYER_Id = "Select uc.* from user_contacts uc inner join user ut on uc.user_id = ut.user_id inner join payer pt on pt.payer_id = ut.payer_id where pt.payer_id = :payerId";
	public static final String GET_UNFILLED_CONTRACTS = "select nt.contract_id,ct.contract_id,ct.payer_id,ct.customer_code,ct.contract_ref,mt.contract_type,mt.material_code from contracts ct inner join payer pt on pt.payer_id = ct.payer_id left join nominations nt on nt.contract_id = ct.contract_id and nt.gas_date=:gasDate inner join (select distinct ct1.payer_id from contracts ct1 inner join payer pt1 on pt1.payer_id = ct1.payer_id inner join nominations nt1 on nt1.contract_id = ct1.contract_id and nt1.gas_date=DATE_ADD(:gasDate, INTERVAL -1 DAY)) as regularpayers on regularpayers.payer_id = ct.payer_id inner join material mt on ct.material_id = mt.material_id where nt.contract_id is null AND DATE_ADD(ct.start_date, INTERVAL -1 DAY)<=:gasDate AND ct.end_date>:gasDate";
	public static final String GET_USER_CONTACT_BY_PAYER_KEY = "Select uc.* from user_contacts uc inner join user ut on uc.user_id = ut.user_id inner join payer pt on pt.payer_id = ut.payer_id where payer_key = :payerKey";

	
	
	public static final String userForOutput = "select a.id as id, a.description as description, a.first_name as first_name, a.last_name as last_name, a.email as email, "
			+ "a.photo_url as photo_url, a.birthdate as birthdate, a.phone_number as phone_number, a.registered_date as registered_date, a.question as question, "
			+ "a.college as college, a.message_counter as message_counter, a.notification_counter as notification_counter, a.save_world as save_world, "
			+ "a.distance as distance, a.enabled_push_notifications as enabled_push_notifications, a.notification_messages as notification_messages, "
			+ "a._timezone as _timezone, a.latitude as latitude, a.longitude as longitude, a.difficulty as difficulty, a.gratitude_privacy as gratitude_privacy, "
			+ "a.allow_notification as allow_notification, a.allow_like_notification as allow_like_notification, a.new_message as new_message, "
			+ "a.new_connection as new_connection, a.friend_joined as friend_joined, a.message_due_date as message_due_date, a.reminder_end_project as reminder_end_project, "
			+ "a.filter_following as filter_following, a.filter_follower as filter_follower, a.number as number, a.interval as interval, a._zip as _zip from GkarmUser a "
			+ "where a.email = :email";
	public static final String fetchTokenId = "select id from UserTokenMappingModel where token = :token";
	public static final String removeByTokenId = "delete from UserTokenMappingModel where id = :id";
	public static final String fetchUserByTokenId = "select a.id as id, concat(a.first_name, ' ', a.last_name) as full_name, a.photo_url as photo_url from GkarmUser a where authorization_id = :token_id";
	public static final String fetchUserByToken = "select a.id as id, a.email as email, a.password as password from GkarmUser as a inner join a.authorization b where b.token = :token";
	public static final String removeTokenFromUser = "update GkarmUser set authorization_id = :authorization_id where email = :email";
	public static final String removeTokenUIDFromUser = "update GkarmUser set authorization_id = :authorization_id and uid = :uid where email = :email";
	public static final String fetchBlockedUserByAuthRecpt = " from GkarmBlock where author_id = :authId and recipient_id = :receiptId";
	public static final String fetchBlockedListByAuthorId = " from GkarmBlock where author_id = :authId";
	public static final String fetchBlockedListByRecipientId = " from GkarmBlock where recipient_id = :receiptId";
	public static final String fetchActiveServeOfSpecificAuthor = " from GkarmServeClients a, a.serve b, a.client c, b.author d where a.status = :serveClientStatus and b.status = :serveStatus and (c.id = :userId or d.id = :userId)";
	public static final String fetchUserById = "select a.id as id, concat(a.first_name, ' ', a.last_name) as full_name, a.photo_url as photo_url, ( case when status=1 then true else false end) as enabled from GkarmUser a where id = :id";
	public static final String fetchSkillByClientId = "select a.id as id, a.gkarmskill as gkarmskill_id, a.gkarmclient as gkarmclient_id from GkarmSkillUser a inner join a.gkarmclient b where b.id = :clientId";
	public static final String fetchTestimonialByRecipientAndStatus= "select a.* from GkarmTestimonial a, a.author b, a.recipient c where a.recipient_id = :recipientId and a.status = :status and c.id not in :authorId";
	public static final String fetchTestimonialByAuthRelatedServeIds = "select a.* from GkarmTestimonial a, a.author b, a.serve c where b.id = :authorId and a.related_id = :relatedId and c.id = :serveId";
	public static final String fetchTestimonialByAuthRelatedReqHelpIds = "select a.* from GkarmTestimonial a, a.author b, a.request_help c where b.id = :authorId and a.related_id = :relatedId and c.id = :requestHelpId";
}

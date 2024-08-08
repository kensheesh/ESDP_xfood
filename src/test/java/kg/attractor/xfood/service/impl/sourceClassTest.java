//package kg.attractor.xfood.service.impl;
//
//import jakarta.mail.MessagingException;
//import kg.attractor.xfood.dto.appeal.AppealSupervisorApproveDto;
//import kg.attractor.xfood.dto.appeal.AppealSupervisorReviewDto;
//import kg.attractor.xfood.dto.appeal.CreateAppealDto;
//import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaSupervisorReviewDto;
//import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
//import kg.attractor.xfood.model.File;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.UnsupportedEncodingException;
//import java.time.LocalDate;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class sourceClassTest {
//
//	private null sourceClassUnderTest;
//
//	@BeforeEach
//	void setUp() {
//		sourceClassUnderTest = new sourceClass();
//	}
//
//	@Test
//	void testGetAppealById() {
//		// Setup
//		final AppealSupervisorReviewDto expectedResult = AppealSupervisorReviewDto.builder()
//				.id(0L)
//				.email("email")
//				.fullName("fullName")
//				.comment("comment")
//				.status(false)
//				.checkListsCriteria(CheckListCriteriaSupervisorReviewDto.builder()
//						.criteria(CriteriaSupervisorShowDto.builder()
//								.id(0L)
//								.description("description")
//								.maxValue(0)
//								.coefficient(0)
//								.zone("name")
//								.section("name")
//								.value(0)
//								.build())
//						.pizzeria("name")
//						.localDate(LocalDate.of(2020, 1, 1))
//						.build())
//				.files(Set.of(File.builder().build()))
//				.build();
//
//		// Run the test
//		final AppealSupervisorReviewDto result = sourceClassUnderTest.getAppealById(0L);
//
//		// Verify the results
//		assertThat(result).isEqualTo(expectedResult);
//	}
//
//	@Test
//	void testUpdate() {
//		// Setup
//		final CreateAppealDto createAppealDto = CreateAppealDto.builder()
//				.fullName("fullName")
//				.comment("comment")
//				.email("email")
//				.tgLinkMessage("tgLinkMessage")
//				.linkToExternalSrc("linkToExternalSrc")
//				.files(new MultipartFile[] {new MockMultipartFile("name", "content".getBytes())})
//				.build();
//
//		// Run the test
//		sourceClassUnderTest.update(createAppealDto, 0L);
//
//		// Verify the results
//	}
//
//	@Test
//	void testApprove() {
//		// Setup
//		final AppealSupervisorApproveDto appealDto = AppealSupervisorApproveDto.builder()
//				.appealId(0L)
//				.respond("respond")
//				.status(false)
//				.build();
//
//		// Run the test
//		sourceClassUnderTest.approve(appealDto);
//
//		// Verify the results
//	}
//
//	@Test
//	void testApprove_ThrowsMessagingException() {
//		// Setup
//		final AppealSupervisorApproveDto appealDto = AppealSupervisorApproveDto.builder()
//				.appealId(0L)
//				.respond("respond")
//				.status(false)
//				.build();
//
//		// Run the test
//		assertThatThrownBy(() -> sourceClassUnderTest.approve(appealDto)).isInstanceOf(MessagingException.class);
//	}
//
//	@Test
//	void testApprove_ThrowsUnsupportedEncodingException() {
//		// Setup
//		final AppealSupervisorApproveDto appealDto = AppealSupervisorApproveDto.builder()
//				.appealId(0L)
//				.respond("respond")
//				.status(false)
//				.build();
//
//		// Run the test
//		assertThatThrownBy(() -> sourceClassUnderTest.approve(appealDto))
//				.isInstanceOf(UnsupportedEncodingException.class);
//	}
//}

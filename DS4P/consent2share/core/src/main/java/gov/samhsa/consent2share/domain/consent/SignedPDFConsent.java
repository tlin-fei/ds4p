/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.domain.consent;

import gov.samhsa.consent2share.domain.DomainEventManager;
import gov.samhsa.consent2share.domain.consent.event.ConsentSignedEvent;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

// TODO: Auto-generated Javadoc
/**
 * The Class SignedPDFConsent.
 */
@Entity
public class SignedPDFConsent extends AbstractSignedPDFDocument{

	/** The signed pdf consent content. */
	@Lob
	@Basic(fetch = FetchType.LAZY)
    private byte[] signedPdfConsentContent;
	
	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	/**
	 * Gets the signed pdf consent content.
	 *
	 * @return the signed pdf consent content
	 */
	public byte[] getSignedPdfConsentContent() {
		return signedPdfConsentContent;
	}

	/**
	 * Sets the signed pdf consent content.
	 *
	 * @param signedPdfConsentContent the new signed pdf consent content
	 */
	public void setSignedPdfConsentContent(byte[] signedPdfConsentContent) {
		this.signedPdfConsentContent = signedPdfConsentContent;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.domain.consent.BinaryContentAccessible#getContent()
	 */
	@Override
	public byte[] getContent() {
		return signedPdfConsentContent;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.domain.consent.BinaryContentAccessible#setContent(byte[])
	 */
	@Override
	public void setContent(byte[] content, Long consentId) {
		this.signedPdfConsentContent=content;
		
		//TODO: It is really bad to put the following line in this weird method: need refactor the consent domain
		DomainEventManager.raise(new ConsentSignedEvent(consentId));
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}


}

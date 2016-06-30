/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.core.codec.support;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.util.MimeType;

/**
 *
 * @author Rossen Stoyanchev
 */
public class CharSequenceEncoder extends AbstractEncoder<CharSequence> {

	private final StringEncoder stringEncoder;


	public CharSequenceEncoder(StringEncoder encoder) {
		super(encoder.getEncodableMimeTypes().toArray(new MimeType[encoder.getEncodableMimeTypes().size()]));
		this.stringEncoder = encoder;
	}


	@Override
	public Flux<DataBuffer> encode(Publisher<? extends CharSequence> inputStream,
			DataBufferFactory bufferFactory, ResolvableType elementType,
			MimeType mimeType, Object... hints) {

		return this.stringEncoder.encode(
				Flux.from(inputStream).map(CharSequence::toString),
				bufferFactory, elementType, mimeType, hints);
	}

}